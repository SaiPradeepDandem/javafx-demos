// Created by Lawrence PC Dol.  Released into the public domain.
//
// Source is licensed for any use, provided this copyright notice is retained.
// No warranty for any purpose whatsoever is implied or expressed.  The author
// is not liable for any losses of any kind, direct or indirect, which result
// from the use of this software.

package com.ezest.javafx.common;

import java.math.*;
import java.util.*;

/**
 * Math Evaluator.  Provides the ability to evaluate a String math expression, with support for functions, variables and
 * standard math constants.
 * <p>
 * Predefined Constants:
 * <ul>
 *   <li>E              - The double value that is closer than any other to e, the base of the natural logarithms (2.718281828459045).
 *   <li>Euler          - Euler's Constant (0.577215664901533).
 *   <li>LN2            - Log of 2 base e (0.693147180559945).
 *   <li>LN10           - Log of 10 base e (2.302585092994046).
 *   <li>LOG2E          - Log of e base 2 (1.442695040888963).
 *   <li>LOG10E         - Log of e base 10 (0.434294481903252).
 *   <li>PHI            - The golden ratio (1.618033988749895).
 *   <li>PI             - The double value that is closer than any other to pi, the ratio of the circumference of a circle to its diameter (3.141592653589793).
 * </ul>
 * <p>
 * Supported Functions (see java.Math for detail and parameters):
 * <ul>
 *   <li>abs
 *   <li>acos
 *   <li>asin
 *   <li>atan
 *   <li>cbrt
 *   <li>ceil
 *   <li>cos
 *   <li>cosh
 *   <li>exp
 *   <li>expm1
 *   <li>floor
 *   <li>log
 *   <li>log10
 *   <li>log1p
 *   <li>max
 *   <li>min
 *   <li>round
 *   <li>roundHE (maps to Math.rint)
 *   <li>signum
 *   <li>sin
 *   <li>sinh
 *   <li>sqrt
 *   <li>tan
 *   <li>tanh
 *   <li>toDegrees
 *   <li>toRadians
 *   <li>ulp
 * </ul>
 * <p>
 * Threading Design : [x] Single Threaded  [ ] Threadsafe  [ ] Immutable  [ ] Isolated
 *
 * @author          Lawrence Dol
 * @since           Build 2008.0426.1016
 */

public class MathEval
extends Object
{

// *************************************************************************************************
// INSTANCE PROPERTIES
// *************************************************************************************************

private SortedMap<String,Double>        constants;                                                  // external constants
private SortedMap<String,Double>        variables;                                                  // external variables
private SortedMap<String,FunctionHandler> functions;                                                // external variables
private boolean                         relaxed;                                                    // allow variables to be undefined

private int                             offset;                                                     // used when returning from a higher precedence sub-expression evaluation
private boolean                         isConstant;                                                 // last expression evaluated is constant

// *************************************************************************************************
// INSTANCE CREATE/DELETE
// *************************************************************************************************

/**
 * Create a math evaluator.
 */
public MathEval() {
    super();

    constants=new TreeMap<String,Double>(String.CASE_INSENSITIVE_ORDER);
    setConstant("E"     ,Math.E);
    setConstant("Euler" ,0.577215664901533D);
    setConstant("LN2"   ,0.693147180559945D);
    setConstant("LN10"  ,2.302585092994046D);
    setConstant("LOG2E" ,1.442695040888963D);
    setConstant("LOG10E",0.434294481903252D);
    setConstant("PHI"   ,1.618033988749895D);
    setConstant("PI"    ,Math.PI);

    variables=new TreeMap<String,Double>(String.CASE_INSENSITIVE_ORDER);

    functions=new TreeMap<String,FunctionHandler>(String.CASE_INSENSITIVE_ORDER);

    offset=0;
    isConstant=false;
    }

/**
 * Create a math evaluator with the same constants, variables, function handlers and relaxation setting as the supplied evaluator.
 */
public MathEval(MathEval oth) {
    super();

    constants=new TreeMap<String,Double>(String.CASE_INSENSITIVE_ORDER);
    constants.putAll(oth.constants);

    variables=new TreeMap<String,Double>(String.CASE_INSENSITIVE_ORDER);
    variables.putAll(oth.variables);

    functions=new TreeMap<String,FunctionHandler>(String.CASE_INSENSITIVE_ORDER);
    functions.putAll(oth.functions);

    relaxed=oth.relaxed;

    offset=0;
    isConstant=false;
    }

// *************************************************************************************************
// INSTANCE METHODS - ACCESSORS
// *************************************************************************************************

/** Set a named constant (constant names are not case-sensitive).  Constants are like variables but are not cleared by clear(). Variables of the same name have precedence over constants. */
public double getConstant(String nam) {
    Double                              val=constants.get(nam);

    return (val==null ? 0 : val.doubleValue());
    }

/** Gets an unmodifiable iterable of the constants in this evaluator. */
public Iterable<Map.Entry<String,Double>> getConstants() {
    return Collections.unmodifiableMap(constants).entrySet();
    }

/** Set a named constant (constants names are not case-sensitive).  Constants are like variables but are not cleared by clear(). Variables of the same name have precedence over constants. */
public MathEval setConstant(String nam, double val) {
    return setConstant(nam,Double.valueOf(val));
    }

/** Set a named constant (constants names are not case-sensitive).  Constants are like variables but are not cleared by clear(). Variables of the same name have precedence over constants. */
public MathEval setConstant(String nam, Double val) {
    if(!Character.isLetter(nam.charAt(0))) { throw new IllegalArgumentException("Constant names must start with a letter"     ); }
    if(nam.indexOf('(')!=-1              ) { throw new IllegalArgumentException("Constant names may not contain a parenthesis"); }
    if(nam.indexOf(')')!=-1              ) { throw new IllegalArgumentException("Constant names may not contain a parenthesis"); }
    if(constants.get(nam)!=null          ) { throw new IllegalArgumentException("Constants may not be redefined"              ); }
    constants.put(nam,val);
    return this;
    }

/** Set a function handler for the specific named function. This replaces the supplied handler for the given name; if the handler is null the function handler is removed. */
public MathEval setFunctionHandler(String nam, FunctionHandler hdl) {
    if(!Character.isLetter(nam.charAt(0))) { throw new IllegalArgumentException("Function names must start with a letter"     ); }
    if(nam.indexOf('(')!=-1              ) { throw new IllegalArgumentException("Function names may not contain a parenthesis"); }
    if(nam.indexOf(')')!=-1              ) { throw new IllegalArgumentException("Function names may not contain a parenthesis"); }
    if(hdl!=null) { functions.put(nam,hdl); }
    else          { functions.remove(nam);  }
    return this;
    }

/** Set a named variable (variables names are not case-sensitive). */
public double getVariable(String nam) {
    Double                              val=variables.get(nam);

    return (val==null ? 0 : val.doubleValue());
    }

/** Gets an unmodifiable iterable of the variables in this evaluator. */
public Iterable<Map.Entry<String,Double>> getVariables() {
    return Collections.unmodifiableMap(variables).entrySet();
    }

/** Set a named variable (variables names are not case-sensitive). */
public MathEval setVariable(String nam, double val) {
    return setVariable(nam,Double.valueOf(val));
    }

/** Set a named variable (variables names are not case-sensitive). If the value is null, the variable is removed. */
public MathEval setVariable(String nam, Double val) {
    if(!Character.isLetter(nam.charAt(0))) { throw new IllegalArgumentException("Variable must start with a letter"           ); }
    if(nam.indexOf('(')!=-1              ) { throw new IllegalArgumentException("Variable names may not contain a parenthesis"); }
    if(nam.indexOf(')')!=-1              ) { throw new IllegalArgumentException("Variable names may not contain a parenthesis"); }
    if(val==null) { variables.remove(nam);  }
    else          { variables.put(nam,val); }
    return this;
    }

/** Clear all variables (constants are not affected). */
public MathEval clear() {
    variables.clear();
    return this;
    }

/** Clear all variables prefixed by the supplied string followed by a dot, such that they match "Prefix.xxx". */
public MathEval clear(String pfx) {
    variables.subMap((pfx+"."),(pfx+"."+Character.MAX_VALUE)).clear();
    return this;
    }

/** Get whether a variable which is used in an expression is required to be explicitly set. If not explicitly set, the value 0.0 is assumed. */
public boolean getVariableRequired() {
    return relaxed;
    }

/** Set whether a variable which is used in an expression is required to be explicitly set. If not explicitly set, the value 0.0 is assumed. */
public MathEval setVariableRequired(boolean val) {
    relaxed=(!val);
    return this;
    }

// *************************************************************************************************
// INSTANCE METHODS - PUBLIC API
// *************************************************************************************************

/**
 * Evaluate this expression.
 */
public double evaluate(String exp) throws NumberFormatException, ArithmeticException {
    isConstant=true;
    return _evaluate(exp);
    }

/**
 * Return whether the previous expression evaluated was constant (i.e. contained no variables).
 * This is useful when optimizing to store the result instead of repeatedly evaluating a constant expression like "2+2".
 */
public boolean previousExpressionConstant() {
    return isConstant;
    }

/** Return a set of the variables in the supplied expression. Note: Substitutions which are in the constant table are not included. */
public Set<String> getVariablesWithin(String exp) {
    Set<String>                         all=new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
    String                              add=null;

    for(StringTokenizer tkz=new StringTokenizer(exp,SYMBOLS,true); tkz.hasMoreTokens(); ) {
        String                          tkn=tkz.nextToken().trim();

        if     (tkn.length()!=0 && Character.isLetter(tkn.charAt(0))) { add=tkn;      }
        else if(tkn.length()==1 && tkn.charAt(0)=='('               ) { add=null;     }
        else if(add!=null       && !constants.containsKey(add)      ) { all.add(add); }
        }
    if(add!=null && !constants.containsKey(add)) { all.add(add); }
    return all;
    }

// *************************************************************************************************
// INSTANCE METHODS - PRIVATE IMPLEMENTATION
// *************************************************************************************************

/**
 * @param exp       Entire expression.
 */
private double _evaluate(String exp) throws NumberFormatException, ArithmeticException {
    return _evaluate(exp,0,(exp.length()-1));
    }

/**
 * @param exp       Entire expression.
 * @param beg       Inclusive begin offset for subexpression.
 * @param end       Inclusive end offset for subexpression.
 */
private double _evaluate(String exp, int beg, int end) throws NumberFormatException, ArithmeticException {
    return _evaluate(exp,beg,end,' ',0,'=');
    }

/**
 * @param exp       Entire expression.
 * @param beg       Inclusive begin offset for subexpression.
 * @param end       Inclusive end offset for subexpression.
 * @param pop       Pending operator (operator previous to this subexpression).
 * @param tot       Running total to initialize this subexpression.
 * @param cop       Current operator (the operator for this subexpression).
 */
private double _evaluate(String exp, int beg, int end, char pop, double tot, char cop) throws NumberFormatException, ArithmeticException {
    char                                nop=0;                                                      // next operator
    int                                 ofs=beg;                                                    // current expression offset

    while(beg<=end && Character.isWhitespace(exp.charAt(beg))) { beg++; }
    if(beg>end) { throw exception(exp,beg,"Blank expression or sub-expression"); }

    OUTER:
    for(ofs=beg; ofs<=end; ofs++) {
        while(ofs<=end && Character.isWhitespace(exp.charAt(ofs))) { ofs++; }
        if(ofs<=end) {
            boolean bkt=false;
            for(beg=ofs; ofs<=end; ofs++) {
                nop=exp.charAt(ofs);
                if(nop=='(') {
                    int cls=skipTo(')',exp,(ofs+1),end,false,false);
                    if(cls>end) { throw exception(exp,ofs,"Unclosed parenthesis"); }
                    bkt=true;
                    ofs=cls;
                    }
                else if(isOperator(nop) && (!isSign(nop) || ofs!=beg)) {                            // break at operator, excluding lead sign character
                    break;
                    }
                }

            char   ch0=exp.charAt(beg);
            double val;

            if(ch0=='(') {
                int rgt=(ofs-1);
                while(rgt>beg && exp.charAt(rgt)!=')') { rgt--; }
                val=_evaluate(exp,(beg+1),(rgt-1));
                }
            else if(Character.isLetter(ch0)) {
                if(bkt) { val=function(exp,beg,(ofs-1)); }
                else    { val=namedVal(exp,beg,(ofs-1)); }
                }
            else {
                String txt;                                                                         // Double and Long parse() functions only accept a string
                if(bkt) {                                                                           // implied multiplication; e.g 2(x+3)
                    ofs=skipTo('(',exp,beg,end,false,false);
                    txt=exp.substring(beg,ofs);                                                     // before decrementing ofs
                    ofs--;                                                                          // back up to before '(' to compensate for outer loop ofs++
                    nop='*';
                    }
                else {
                    txt=exp.substring(beg,ofs).trim();
                    }
                try {
                    if(stringSW(txt,"0x")) { val=(double)Long.parseLong(txt.substring(2),16); }
                    else                   { val=Double.parseDouble(txt);                     }
                    }
                catch(NumberFormatException thr) { throw exception(exp,beg,"Invalid numeric value \""+txt+"\""); }
                }

            if(opPrecedence(nop)>opPrecedence(cop)) {                                               // correct even for last (non-operator) character, since non-operator have the artificial "precedence" zero
                val=_evaluate(exp,(ofs+1),end,cop,val,nop);                                         // from after operator to end of current subexpression
                ofs=offset;                                                                         // modify offset
                nop=exp.charAt(ofs<=end ? ofs : end);                                               // modify next operator
                }

            try {
                switch(cop) {
                    case '=' : { tot=val;               } break;
                    case '+' : { tot=(tot+val);         } break;
                    case '-' : { tot=(tot-val);         } break;
                    case '*' : { tot=(tot*val);         } break;
                    case '/' : { tot=(tot/val);         } break;
                    case '%' : { tot=(tot%val);         } break;
                    case '^' : { tot=Math.pow(tot,val); } break;
                    default  : {
                        int tmp=beg;
                        while(tmp>0 && !isOperator(exp.charAt(tmp))) { tmp--; }
                        throw exception(exp,tmp,"Operator \""+cop+"\" not handled by math engine (Programmer error: The list of operators is inconsistent with the engine)");
                        }
                    }
                }
            catch(ArithmeticException thr) { throw exception(exp,beg,"Mathematical expression \""+exp+"\" failed to evaluate",thr); }
            cop=nop;
            if(opPrecedence(cop)<=opPrecedence(pop)) { break OUTER; }
            }
        if(ofs==end && isOperator(nop)) { throw exception(exp,ofs,"Expression ends with a blank sub-expression"); }
        }
    offset=ofs;
    return tot;
    }

private double function(String exp, int beg, int end) {
    int                                 argbeg,argend;
    ArgParser                           fncargs;

    for(argbeg=beg; argbeg<=end    && exp.charAt(argbeg)!='('; argbeg++) {;}
    for(argend=end; argbeg<=argend && exp.charAt(argend)!=')'; argend--) {;}

    fncargs=new ArgParser(exp,argbeg,argend);

    try {
        String                          fncnam=exp.substring(beg,argbeg).trim();
        FunctionHandler                 fnchdl=functions.get(fncnam);

        if(fnchdl!=null) {
            return fnchdl.evaluateFunction(fncnam,fncargs);                                         // Allow registered functions to preempt builtins
            }
        else switch(Character.toLowerCase(fncnam.charAt(0))) {
            case 'a' : {
                if(fncnam.equalsIgnoreCase("abs"           )) { return Math.abs        (fncargs.next());                }
                if(fncnam.equalsIgnoreCase("acos"          )) { return Math.acos       (fncargs.next());                }
                if(fncnam.equalsIgnoreCase("asin"          )) { return Math.asin       (fncargs.next());                }
                if(fncnam.equalsIgnoreCase("atan"          )) { return Math.atan       (fncargs.next());                }
                } break;
            case 'c': {
                if(fncnam.equalsIgnoreCase("cbrt"          )) { return Math.cbrt       (fncargs.next());                }
                if(fncnam.equalsIgnoreCase("ceil"          )) { return Math.ceil       (fncargs.next());                }
                if(fncnam.equalsIgnoreCase("cos"           )) { return Math.cos        (fncargs.next());                }
                if(fncnam.equalsIgnoreCase("cosh"          )) { return Math.cosh       (fncargs.next());                }
                } break;
            case 'e': {
                if(fncnam.equalsIgnoreCase("exp"           )) { return Math.exp        (fncargs.next());                }
                if(fncnam.equalsIgnoreCase("expm1"         )) { return Math.expm1      (fncargs.next());                }
                } break;
            case 'f': {
                if(fncnam.equalsIgnoreCase("floor"         )) { return Math.floor      (fncargs.next());                }
                } break;
            case 'g': {
//              if(fncnam.equalsIgnoreCase("getExponent"   )) { return Math.getExponent(fncargs.next());                } needs Java 6
                } break;
            case 'l': {
                if(fncnam.equalsIgnoreCase("log"           )) { return Math.log        (fncargs.next());                }
                if(fncnam.equalsIgnoreCase("log10"         )) { return Math.log10      (fncargs.next());                }
                if(fncnam.equalsIgnoreCase("log1p"         )) { return Math.log1p      (fncargs.next());                }
                } break;
            case 'm': {
                if(fncnam.equalsIgnoreCase("max"           )) { return Math.max        (fncargs.next(),fncargs.next()); }
                if(fncnam.equalsIgnoreCase("min"           )) { return Math.min        (fncargs.next(),fncargs.next()); }
                } break;
            case 'n': {
//              if(fncnam.equalsIgnoreCase("nextUp"        )) { return Math.nextUp     (fncargs.next());                } needs Java 6
                } break;
            case 'r': {
                if(fncnam.equalsIgnoreCase("round"         )) { return Math.round      (fncargs.next());                }
                if(fncnam.equalsIgnoreCase("roundHE"       )) { return Math.rint       (fncargs.next());                } // round half-even
                } break;
            case 's': {
                if(fncnam.equalsIgnoreCase("signum"        )) { return Math.signum     (fncargs.next());                }
                if(fncnam.equalsIgnoreCase("sin"           )) { return Math.sin        (fncargs.next());                }
                if(fncnam.equalsIgnoreCase("sinh"          )) { return Math.sinh       (fncargs.next());                }
                if(fncnam.equalsIgnoreCase("sqrt"          )) { return Math.sqrt       (fncargs.next());                }
                } break;
            case 't': {
                if(fncnam.equalsIgnoreCase("tan"           )) { return Math.tan        (fncargs.next());                }
                if(fncnam.equalsIgnoreCase("tanh"          )) { return Math.tanh       (fncargs.next());                }
                if(fncnam.equalsIgnoreCase("toDegrees"     )) { return Math.toDegrees  (fncargs.next());                }
                if(fncnam.equalsIgnoreCase("toRadians"     )) { return Math.toRadians  (fncargs.next());                }
                } break;
            case 'u': {
                if(fncnam.equalsIgnoreCase("ulp"           )) { return Math.ulp        (fncargs.next());                }
                } break;
            // no default
            }

        isConstant=false;

        // INCONSTANT VALUE FUNCTIONS
        if(fncnam.equalsIgnoreCase("random"                )) { return Math.random     ();                              }

        fncargs=null;                                                                               // suppress check for too many fncargs
        }
    catch(ArithmeticException thr) {
        int ofs=fncargs.getStart();                                                                 // save current arg start offset
        fncargs=null;                                                                               // suppress check for too many fncargs
        throw exception(exp,ofs,"Function evaluation failed",thr);
        }
    catch(IllegalArgumentException thr) {
        throw exception(exp,(fncargs!=null ? fncargs.getIndex() : beg),thr.getMessage());
        }
    catch(IndexOutOfBoundsException thr) {
        if(fncargs!=null && !fncargs.hasNext()) { throw exception(exp,fncargs.getIndex(),"Function has too few arguments"); }
        throw exception(exp,beg,"Unexpected exception parsing function arguments",thr);
        }
    catch(NoSuchMethodError thr) {
        fncargs=null;                                                                               // suppress check for too many fncargs
        throw exception(exp,beg,"Function not supported in this JVM: \""+exp.substring(beg,argbeg)+"\"");
        }
    catch(Throwable thr) {
        fncargs=null;                                                                               // suppress check for too many fncargs
        throw exception(exp,beg,"Unexpected exception parsing function arguments",thr);
        }
    finally {
        if(fncargs!=null && fncargs.hasNext()) { throw exception(exp,fncargs.getIndex(),"Function has too many arguments"); }
        }
    throw exception(exp,beg,"Function \""+exp.substring(beg,argbeg)+"\" not recognized");
    }

private double namedVal(String exp, int beg, int end) {
    while(beg<=end && Character.isWhitespace(exp.charAt(end))) { end--; }
    if(beg>end) { throw exception(exp,beg,"Blank expression or sub-expression"); }

    String                  nam=exp.substring(beg,(end+1));
    Double                  val;

    if     ((val=constants.get(nam))!=null) {                   return val.doubleValue(); }
    else if((val=variables.get(nam))!=null) { isConstant=false; return val.doubleValue(); }
    else if(relaxed                       ) { isConstant=false; return 0.0;               }

    throw exception(exp,beg,"Unrecognized constant or variable \""+exp.substring(beg,(end+1))+"\"");
    }

private boolean isSign(char chr) {
    return (chr=='-' || chr=='+');
    }

private boolean isOperator(char chr) {
    return (chr<128 && PRECEDENCE[chr]!=0);
    }

private int opPrecedence(char chr) {
    return (chr<128 ? PRECEDENCE[chr] : 0);
    }

// *************************************************************************************************
// INSTANCE INNER CLASSES
// *************************************************************************************************

// *************************************************************************************************
// STATIC NESTED CLASSES - FUNCTION ARGUMENT PARSER
// *************************************************************************************************

    public final class ArgParser
    {
    final String                        expression;
    final int                           excEnd;

    int                                 index;
    int                                 argStart;

    ArgParser(String exp, int excstr, int excend) {
        expression=exp;
        excEnd=excend;

        index=argStart=(excstr+1);

        while(index<excEnd && Character.isWhitespace(expression.charAt(index))) { index++; }
        }

    public double next() {
        return next(Double.NaN);
        }

    public double next(double dft) {
        if(index>=excEnd) {
            if(Double.isNaN(dft)) { throw new IndexOutOfBoundsException("Function has too few arguments"); }
            return dft;
            }

        int                             end,nst;

        if(expression.charAt(index)==',') { index++; }
        argStart=index;
        for(nst=0; index<excEnd && nst>=0; index++) {
            char chr=expression.charAt(index);
            if     (chr=='(') { nst++; }
            else if(chr==')') { nst--; }
            if(nst==0 && chr==',') { break; }                                                       // skip past ',' and break
            }
        if(nst!=0) { throw exception(expression,index,"Function has mismatched parenthesis"); }     // s/never be
        return _evaluate(expression.substring(argStart,index).trim());
        }

    public boolean hasNext() {
        return (index<excEnd);
        }

    int getStart() {
        return argStart;
        }

    int getIndex() {
        return index;
        }
    }

// *************************************************************************************************
// STATIC NESTED CLASSES - FUNCTION EVALUATOR
// *************************************************************************************************

    static public interface FunctionHandler
    {
    public double evaluateFunction(String nam, ArgParser args) throws ArithmeticException;
    }

// *************************************************************************************************
// STATIC PROPERTIES
// *************************************************************************************************

static private final int[]              PRECEDENCE;
static {
    int[] prc=new int[127];
    prc['+']=1;
    prc['-']=1;
    prc['*']=2;
    prc['/']=2;
    prc['%']=2;
    prc['^']=3;
    prc['=']=Integer.MAX_VALUE;
    PRECEDENCE=prc;
    }

static private final String             SYMBOLS="+-*/%^()";

// *************************************************************************************************
// STATIC INIT & MAIN
// *************************************************************************************************

// *************************************************************************************************
// STATIC METHODS - UTILITY
// *************************************************************************************************

static private ArithmeticException exception(String exp, int ofs, String txt) {
    return new ArithmeticException(txt+" at offset "+ofs+" in expression \""+exp+"\"");
    }

static private ArithmeticException exception(String exp, int ofs, String txt, Throwable thr) {
    String                              cau=(thr.getMessage()!=null ? thr.getMessage() : thr.toString());

    return new ArithmeticException(txt+" at offset "+ofs+" in expression \""+exp+"\""+" (Cause: "+cau+")");
    }

static private boolean stringSW(String str, String val) {
    if(str==null) { return (val==null); }
    if(val==null) { return false;      }
    return str.regionMatches(true,0,val,0,val.length());
    }

static private int skipTo(char chr, String str, int beg, int end, boolean ignqut, boolean ignesc) {
    int                                 ofs=beg,lvl=1;
    boolean                             nst,qut,esc;                                                // nesting, quoting, escaping
    char                                opn;

    if     (chr==')' ) { nst=true;  opn='('; qut=ignqut; esc=false;  }
    else if(chr==']' ) { nst=true;  opn='['; qut=ignqut; esc=false;  }
    else if(chr=='}' ) { nst=true;  opn='{'; qut=ignqut; esc=false;  }
    else if(chr=='>' ) { nst=true;  opn='<'; qut=ignqut; esc=false;  }
    else if(chr=='"' ) { nst=false; opn=0;   qut=false;  esc=ignesc; }
    else if(chr=='\'') { nst=false; opn=0;   qut=false;  esc=ignesc; }
    else               { nst=false; opn=0;   qut=ignqut; esc=ignesc; }

    for(; ofs<=end; ofs++) {
        char cur=str.charAt(ofs);
        if     (cur==opn  && nst) { lvl++; continue;                                  }
        else if(cur=='\'' && qut) { ofs=skipTo(cur,str,(ofs+1),end,false,ignesc); }
        else if(cur=='\"' && qut) { ofs=skipTo(cur,str,(ofs+1),end,false,ignesc); }
        else if(cur==chr) {
            if((!esc || !isEscaped(str,beg,ofs)) && (--lvl)==0) { break; }
            }
        }
    return ofs;
    }

static private boolean isEscaped(String str, int beg, int ofs) {
    boolean                             ie=false;

    while(ofs>beg && str.charAt(--ofs)=='\\') { ie=!ie; }
    return ie;
    }

} // END PUBLIC CLASS
