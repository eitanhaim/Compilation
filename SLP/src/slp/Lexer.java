/* The following code was generated by JFlex 1.4.3 on 18:07 23/11/15 */

package slp;

import java_cup.runtime.*;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 18:07 23/11/15 from the specification file
 * <tt>/Users/eitanmashiah/git/Compilation/SLP/src/slp/slp.lex</tt>
 */
class Lexer implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int STRING = 2;
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\3\1\2\1\0\1\3\1\1\22\0\1\3\1\55\1\63"+
    "\2\0\1\53\1\60\1\0\1\40\1\41\1\5\1\51\1\47\1\52"+
    "\1\50\1\4\1\12\11\11\1\0\1\46\1\57\1\54\1\56\2\0"+
    "\32\6\1\44\1\13\1\45\1\0\1\7\1\0\1\16\1\30\1\14"+
    "\1\24\1\20\1\34\1\32\1\36\1\25\1\10\1\37\1\15\1\10"+
    "\1\23\1\27\1\62\1\10\1\31\1\17\1\22\1\33\1\26\1\35"+
    "\1\21\2\10\1\42\1\61\1\43\uff82\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\2\2\1\3\1\4\1\5\1\6\1\5"+
    "\2\7\14\5\1\10\1\11\1\12\1\13\1\14\1\15"+
    "\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25"+
    "\1\26\1\27\2\1\1\5\1\30\1\31\2\32\1\1"+
    "\1\33\1\2\1\0\1\34\13\5\1\35\6\5\1\36"+
    "\1\37\1\40\1\41\1\42\1\43\1\5\1\44\1\45"+
    "\1\46\1\47\1\50\2\0\11\5\1\51\1\5\1\52"+
    "\10\5\1\0\1\2\5\5\1\53\1\5\1\54\1\55"+
    "\1\56\1\57\7\5\1\60\6\5\1\61\1\62\1\5"+
    "\1\63\1\64\1\65\1\5\1\66\1\67\1\70\2\5"+
    "\1\71\1\5\1\72\1\73\1\74";

  private static int [] zzUnpackAction() {
    int [] result = new int[148];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\64\0\150\0\234\0\150\0\320\0\150\0\u0104"+
    "\0\u0138\0\u016c\0\u01a0\0\150\0\u01d4\0\u0208\0\u023c\0\u0270"+
    "\0\u02a4\0\u02d8\0\u030c\0\u0340\0\u0374\0\u03a8\0\u03dc\0\u0410"+
    "\0\150\0\150\0\150\0\150\0\150\0\150\0\150\0\150"+
    "\0\150\0\150\0\150\0\150\0\u0444\0\u0478\0\u04ac\0\u04e0"+
    "\0\u0514\0\u0548\0\u057c\0\150\0\u05b0\0\u05e4\0\150\0\u0618"+
    "\0\150\0\u064c\0\u0680\0\u06b4\0\u06e8\0\u071c\0\u0750\0\u0784"+
    "\0\u07b8\0\u07ec\0\u0820\0\u0854\0\u0888\0\u08bc\0\u08f0\0\u016c"+
    "\0\u0924\0\u0958\0\u098c\0\u09c0\0\u09f4\0\u0a28\0\150\0\150"+
    "\0\150\0\150\0\150\0\150\0\u0a5c\0\150\0\150\0\150"+
    "\0\150\0\150\0\u0a90\0\u0ac4\0\u0af8\0\u0b2c\0\u0b60\0\u0b94"+
    "\0\u0bc8\0\u0bfc\0\u0c30\0\u0c64\0\u0c98\0\u016c\0\u0ccc\0\u016c"+
    "\0\u0d00\0\u0d34\0\u0d68\0\u0d9c\0\u0dd0\0\u0e04\0\u0e38\0\u0e6c"+
    "\0\u0ea0\0\u0a90\0\u0ed4\0\u0f08\0\u0f3c\0\u0f70\0\u0fa4\0\u016c"+
    "\0\u0fd8\0\u016c\0\u016c\0\u016c\0\u016c\0\u100c\0\u1040\0\u1074"+
    "\0\u10a8\0\u10dc\0\u1110\0\u1144\0\u016c\0\u1178\0\u11ac\0\u11e0"+
    "\0\u1214\0\u1248\0\u127c\0\u016c\0\u016c\0\u12b0\0\u016c\0\u016c"+
    "\0\u016c\0\u12e4\0\u016c\0\u016c\0\u016c\0\u1318\0\u134c\0\u016c"+
    "\0\u1380\0\u016c\0\u016c\0\u016c";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[148];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\1\4\2\5\1\6\1\7\1\10\1\11\1\12"+
    "\1\13\1\14\1\3\1\15\1\16\1\12\1\17\1\20"+
    "\1\12\1\21\1\22\1\12\1\23\1\24\1\12\1\25"+
    "\1\26\2\12\1\27\1\30\2\12\1\31\1\32\1\33"+
    "\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43"+
    "\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53"+
    "\1\54\1\55\1\56\1\57\10\55\1\60\47\55\1\61"+
    "\66\0\1\5\65\0\1\62\1\63\64\0\1\10\1\64"+
    "\1\10\2\64\1\0\24\10\22\0\1\10\7\0\5\11"+
    "\1\0\24\11\22\0\1\11\7\0\1\12\1\11\1\12"+
    "\2\11\1\0\24\12\22\0\1\12\12\0\2\13\57\0"+
    "\1\12\1\11\1\12\2\11\1\0\1\12\1\65\11\12"+
    "\1\66\10\12\22\0\1\12\7\0\1\12\1\11\1\12"+
    "\2\11\1\0\4\12\1\67\17\12\22\0\1\12\7\0"+
    "\1\12\1\11\1\12\2\11\1\0\6\12\1\70\15\12"+
    "\22\0\1\12\7\0\1\12\1\11\1\12\2\11\1\0"+
    "\1\12\1\71\3\12\1\72\16\12\22\0\1\12\7\0"+
    "\1\12\1\11\1\12\2\11\1\0\15\12\1\73\4\12"+
    "\1\74\1\12\22\0\1\12\7\0\1\12\1\11\1\12"+
    "\2\11\1\0\4\12\1\75\12\12\1\76\4\12\22\0"+
    "\1\12\7\0\1\12\1\11\1\12\2\11\1\0\7\12"+
    "\1\77\10\12\1\100\3\12\22\0\1\12\7\0\1\12"+
    "\1\11\1\12\2\11\1\0\13\12\1\101\10\12\22\0"+
    "\1\12\7\0\1\12\1\11\1\12\2\11\1\0\13\12"+
    "\1\102\1\12\1\103\6\12\22\0\1\12\7\0\1\12"+
    "\1\11\1\12\2\11\1\0\4\12\1\104\17\12\22\0"+
    "\1\12\7\0\1\12\1\11\1\12\2\11\1\0\2\12"+
    "\1\105\21\12\22\0\1\12\7\0\1\12\1\11\1\12"+
    "\2\11\1\0\22\12\1\106\1\12\22\0\1\12\55\0"+
    "\1\107\63\0\1\110\63\0\1\111\63\0\1\112\67\0"+
    "\1\113\64\0\1\114\10\0\1\12\1\11\1\12\2\11"+
    "\1\0\15\12\1\115\6\12\22\0\1\12\1\0\1\55"+
    "\2\0\10\55\1\0\47\55\3\0\1\57\61\0\2\116"+
    "\1\0\10\116\1\117\6\116\1\120\1\121\37\116\1\122"+
    "\1\62\1\4\1\5\61\62\5\123\1\124\56\123\6\0"+
    "\5\64\1\0\24\64\22\0\1\64\7\0\1\12\1\11"+
    "\1\12\2\11\1\0\2\12\1\125\21\12\22\0\1\12"+
    "\7\0\1\12\1\11\1\12\2\11\1\0\7\12\1\126"+
    "\14\12\22\0\1\12\7\0\1\12\1\11\1\12\2\11"+
    "\1\0\7\12\1\127\14\12\22\0\1\12\7\0\1\12"+
    "\1\11\1\12\2\11\1\0\2\12\1\130\12\12\1\131"+
    "\6\12\22\0\1\12\7\0\1\12\1\11\1\12\2\11"+
    "\1\0\3\12\1\132\20\12\22\0\1\12\7\0\1\12"+
    "\1\11\1\12\2\11\1\0\6\12\1\133\15\12\22\0"+
    "\1\12\7\0\1\12\1\11\1\12\2\11\1\0\17\12"+
    "\1\134\4\12\22\0\1\12\7\0\1\12\1\11\1\12"+
    "\2\11\1\0\11\12\1\135\12\12\22\0\1\12\7\0"+
    "\1\12\1\11\1\12\2\11\1\0\21\12\1\136\2\12"+
    "\22\0\1\12\7\0\1\12\1\11\1\12\2\11\1\0"+
    "\1\12\1\137\22\12\22\0\1\12\7\0\1\12\1\11"+
    "\1\12\2\11\1\0\6\12\1\140\15\12\22\0\1\12"+
    "\7\0\1\12\1\11\1\12\2\11\1\0\11\12\1\141"+
    "\12\12\22\0\1\12\7\0\1\12\1\11\1\12\2\11"+
    "\1\0\13\12\1\142\10\12\22\0\1\12\7\0\1\12"+
    "\1\11\1\12\2\11\1\0\4\12\1\143\17\12\22\0"+
    "\1\12\7\0\1\12\1\11\1\12\2\11\1\0\2\12"+
    "\1\144\3\12\1\145\15\12\22\0\1\12\7\0\1\12"+
    "\1\11\1\12\2\11\1\0\1\12\1\146\22\12\22\0"+
    "\1\12\7\0\1\12\1\11\1\12\2\11\1\0\11\12"+
    "\1\147\12\12\22\0\1\12\7\0\1\12\1\11\1\12"+
    "\2\11\1\0\11\12\1\150\12\12\22\0\1\12\1\0"+
    "\5\123\1\151\62\123\1\152\1\151\56\123\6\0\1\12"+
    "\1\11\1\12\2\11\1\0\3\12\1\153\20\12\22\0"+
    "\1\12\7\0\1\12\1\11\1\12\2\11\1\0\6\12"+
    "\1\154\15\12\22\0\1\12\7\0\1\12\1\11\1\12"+
    "\2\11\1\0\16\12\1\155\5\12\22\0\1\12\7\0"+
    "\1\12\1\11\1\12\2\11\1\0\6\12\1\156\15\12"+
    "\22\0\1\12\7\0\1\12\1\11\1\12\2\11\1\0"+
    "\11\12\1\157\12\12\22\0\1\12\7\0\1\12\1\11"+
    "\1\12\2\11\1\0\4\12\1\160\17\12\22\0\1\12"+
    "\7\0\1\12\1\11\1\12\2\11\1\0\4\12\1\161"+
    "\17\12\22\0\1\12\7\0\1\12\1\11\1\12\2\11"+
    "\1\0\4\12\1\162\17\12\22\0\1\12\7\0\1\12"+
    "\1\11\1\12\2\11\1\0\3\12\1\163\20\12\22\0"+
    "\1\12\7\0\1\12\1\11\1\12\2\11\1\0\1\12"+
    "\1\164\22\12\22\0\1\12\7\0\1\12\1\11\1\12"+
    "\2\11\1\0\10\12\1\165\13\12\22\0\1\12\7\0"+
    "\1\12\1\11\1\12\2\11\1\0\1\12\1\166\22\12"+
    "\22\0\1\12\7\0\1\12\1\11\1\12\2\11\1\0"+
    "\2\12\1\167\21\12\22\0\1\12\7\0\1\12\1\11"+
    "\1\12\2\11\1\0\10\12\1\170\13\12\22\0\1\12"+
    "\7\0\1\12\1\11\1\12\2\11\1\0\17\12\1\171"+
    "\4\12\22\0\1\12\7\0\1\12\1\11\1\12\2\11"+
    "\1\0\3\12\1\172\20\12\22\0\1\12\7\0\1\12"+
    "\1\11\1\12\2\11\1\0\1\12\1\173\22\12\22\0"+
    "\1\12\7\0\1\12\1\11\1\12\2\11\1\0\7\12"+
    "\1\174\14\12\22\0\1\12\1\0\4\123\1\5\1\151"+
    "\56\123\6\0\1\12\1\11\1\12\2\11\1\0\3\12"+
    "\1\175\20\12\22\0\1\12\7\0\1\12\1\11\1\12"+
    "\2\11\1\0\11\12\1\176\12\12\22\0\1\12\7\0"+
    "\1\12\1\11\1\12\2\11\1\0\6\12\1\177\15\12"+
    "\22\0\1\12\7\0\1\12\1\11\1\12\2\11\1\0"+
    "\11\12\1\200\12\12\22\0\1\12\7\0\1\12\1\11"+
    "\1\12\2\11\1\0\7\12\1\201\14\12\22\0\1\12"+
    "\7\0\1\12\1\11\1\12\2\11\1\0\7\12\1\202"+
    "\14\12\22\0\1\12\7\0\1\12\1\11\1\12\2\11"+
    "\1\0\4\12\1\203\17\12\22\0\1\12\7\0\1\12"+
    "\1\11\1\12\2\11\1\0\23\12\1\204\22\0\1\12"+
    "\7\0\1\12\1\11\1\12\2\11\1\0\11\12\1\205"+
    "\12\12\22\0\1\12\7\0\1\12\1\11\1\12\2\11"+
    "\1\0\15\12\1\206\6\12\22\0\1\12\7\0\1\12"+
    "\1\11\1\12\2\11\1\0\4\12\1\207\17\12\22\0"+
    "\1\12\7\0\1\12\1\11\1\12\2\11\1\0\4\12"+
    "\1\210\17\12\22\0\1\12\7\0\1\12\1\11\1\12"+
    "\2\11\1\0\6\12\1\211\15\12\22\0\1\12\7\0"+
    "\1\12\1\11\1\12\2\11\1\0\7\12\1\212\14\12"+
    "\22\0\1\12\7\0\1\12\1\11\1\12\2\11\1\0"+
    "\22\12\1\213\1\12\22\0\1\12\7\0\1\12\1\11"+
    "\1\12\2\11\1\0\1\214\23\12\22\0\1\12\7\0"+
    "\1\12\1\11\1\12\2\11\1\0\16\12\1\215\5\12"+
    "\22\0\1\12\7\0\1\12\1\11\1\12\2\11\1\0"+
    "\10\12\1\216\13\12\22\0\1\12\7\0\1\12\1\11"+
    "\1\12\2\11\1\0\2\12\1\217\21\12\22\0\1\12"+
    "\7\0\1\12\1\11\1\12\2\11\1\0\7\12\1\220"+
    "\14\12\22\0\1\12\7\0\1\12\1\11\1\12\2\11"+
    "\1\0\17\12\1\221\4\12\22\0\1\12\7\0\1\12"+
    "\1\11\1\12\2\11\1\0\3\12\1\222\20\12\22\0"+
    "\1\12\7\0\1\12\1\11\1\12\2\11\1\0\7\12"+
    "\1\223\14\12\22\0\1\12\7\0\1\12\1\11\1\12"+
    "\2\11\1\0\4\12\1\224\17\12\22\0\1\12\1\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[5044];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\1\11\1\1\1\11\1\1\1\11\4\1\1\11"+
    "\14\1\14\11\7\1\1\11\2\1\1\11\1\1\1\11"+
    "\1\1\1\0\23\1\6\11\1\1\5\11\2\0\24\1"+
    "\1\0\53\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[148];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
	StringBuilder string = new StringBuilder();
	public int getLineNumber() { return yyline+1; }


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  Lexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  Lexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 132) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) throws RuntimeException {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new RuntimeException(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  throws RuntimeException {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public Token next_token() throws java.io.IOException, RuntimeException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          zzR = false;
          break;
        case '\r':
          yyline++;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
          }
          break;
        default:
          zzR = false;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 50: 
          { return new Token(yyline, yytext(), sym.READI);
          }
        case 61: break;
        case 35: 
          { return new Token(yyline, yytext(), sym.LOR);
          }
        case 62: break;
        case 54: 
          { return new Token(yyline, yytext(), sym.LENGTH);
          }
        case 63: break;
        case 17: 
          { return new Token(yyline, yytext(), sym.PLUS);
          }
        case 64: break;
        case 52: 
          { return new Token(yyline, yytext(), sym.WHILE);
          }
        case 65: break;
        case 44: 
          { return new Token(yyline, yytext(), sym.TRUE);
          }
        case 66: break;
        case 30: 
          { return new Token(yyline, yytext(), sym.EQUAL);
          }
        case 67: break;
        case 49: 
          { return new Token(yyline, yytext(), sym.BREAK);
          }
        case 68: break;
        case 5: 
          { return new Token(yyline, "VAR", sym.VAR, yytext());
          }
        case 69: break;
        case 38: 
          { string.append('\t');
          }
        case 70: break;
        case 47: 
          { return new Token(yyline, yytext(), sym.VOID);
          }
        case 71: break;
        case 24: 
          { string.setLength(0); yybegin(STRING);
          }
        case 72: break;
        case 43: 
          { return new Token(yyline, yytext(), sym.ELSE);
          }
        case 73: break;
        case 42: 
          { return new Token(yyline, yytext(), sym.INT);
          }
        case 74: break;
        case 4: 
          { return new Token(yyline, yytext(), sym.MULTIPLY);
          }
        case 75: break;
        case 20: 
          { return new Token(yyline, yytext(), sym.ASSIGN);
          }
        case 76: break;
        case 57: 
          { return new Token(yyline, yytext(), sym.RETURN);
          }
        case 77: break;
        case 2: 
          { /* just skip what was found, do nothing */
          }
        case 78: break;
        case 37: 
          { string.append('\\');
          }
        case 79: break;
        case 21: 
          { return new Token(yyline, yytext(), sym.LNEG);
          }
        case 80: break;
        case 45: 
          { return new Token(yyline, yytext(), sym.THIS);
          }
        case 81: break;
        case 60: 
          { return new Token(yyline, yytext(), sym.CONTINUE);
          }
        case 82: break;
        case 18: 
          { return new Token(yyline, yytext(), sym.MINUS);
          }
        case 83: break;
        case 9: 
          { return new Token(yyline, yytext(), sym.RP);
          }
        case 84: break;
        case 22: 
          { return new Token(yyline, yytext(), sym.GT);
          }
        case 85: break;
        case 53: 
          { return new Token(yyline, yytext(), sym.PRINT);
          }
        case 86: break;
        case 19: 
          { return new Token(yyline, yytext(), sym.MOD);
          }
        case 87: break;
        case 23: 
          { return new Token(yyline, yytext(), sym.LT);
          }
        case 88: break;
        case 14: 
          { return new Token(yyline, yytext(), sym.SEMI);
          }
        case 89: break;
        case 55: 
          { return new Token(yyline, yytext(), sym.STATIC);
          }
        case 90: break;
        case 58: 
          { return new Token(yyline, yytext(), sym.EXTENDS);
          }
        case 91: break;
        case 15: 
          { return new Token(yyline, yytext(), sym.COMMA);
          }
        case 92: break;
        case 40: 
          { string.append('\"');
          }
        case 93: break;
        case 12: 
          { return new Token(yyline, yytext(), sym.LB);
          }
        case 94: break;
        case 51: 
          { return new Token(yyline, yytext(), sym.FALSE);
          }
        case 95: break;
        case 48: 
          { return new Token(yyline, yytext(), sym.CLASS);
          }
        case 96: break;
        case 33: 
          { return new Token(yyline, yytext(), sym.LTE);
          }
        case 97: break;
        case 28: 
          { return new Token(yyline, "CLASS_ID", sym.CLASS_ID, yytext());
          }
        case 98: break;
        case 1: 
          { throw new RuntimeException("Lexical error: Illegal character at line " + (yyline+1) + " : '" + yytext() + "'");
          }
        case 99: break;
        case 31: 
          { return new Token(yyline, yytext(), sym.NEQUAL);
          }
        case 100: break;
        case 39: 
          { string.append('\n');
          }
        case 101: break;
        case 46: 
          { return new Token(yyline, yytext(), sym.NULL);
          }
        case 102: break;
        case 27: 
          { yybegin(YYINITIAL);
						return new Token(yyline, "QUOTE", sym.QUOTE, string.toString());
          }
        case 103: break;
        case 25: 
          { string.append(yytext());
          }
        case 104: break;
        case 6: 
          { return new Token(yyline, "ID", sym.ID, yytext());
          }
        case 105: break;
        case 36: 
          { throw new RuntimeException("Lexical error: Illegal character at line " + (yyline+1) + " : \"" + yytext() + "\"");
          }
        case 106: break;
        case 3: 
          { return new Token(yyline, yytext(), sym.DIVIDE);
          }
        case 107: break;
        case 41: 
          { return new Token(yyline, yytext(), sym.NEW);
          }
        case 108: break;
        case 29: 
          { return new Token(yyline, yytext(), sym.IF);
          }
        case 109: break;
        case 10: 
          { return new Token(yyline, yytext(), sym.LCBR);
          }
        case 110: break;
        case 56: 
          { return new Token(yyline, yytext(), sym.STRING);
          }
        case 111: break;
        case 26: 
          { throw new RuntimeException("Lexical error: Unterminated string at end of line " + (yyline+1));
          }
        case 112: break;
        case 8: 
          { return new Token(yyline, yytext(), sym.LP);
          }
        case 113: break;
        case 34: 
          { return new Token(yyline, yytext(), sym.LAND);
          }
        case 114: break;
        case 11: 
          { return new Token(yyline, yytext(), sym.RCBR);
          }
        case 115: break;
        case 59: 
          { return new Token(yyline, yytext(), sym.BOOLEAN);
          }
        case 116: break;
        case 7: 
          { return new Token(yyline, "INTEGER", sym.INTEGER, new Integer(yytext()));
          }
        case 117: break;
        case 13: 
          { return new Token(yyline, yytext(), sym.RB);
          }
        case 118: break;
        case 32: 
          { return new Token(yyline, yytext(), sym.GTE);
          }
        case 119: break;
        case 16: 
          { return new Token(yyline, yytext(), sym.DOT);
          }
        case 120: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              {
                return new Token(yyline, "EOF", sym.EOF);
              }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
