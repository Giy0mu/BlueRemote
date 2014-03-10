package com.korigan.request;

import java.nio.ByteBuffer;
import java.util.Hashtable;

import android.util.Log;
import android.view.KeyEvent;

public class KeyRequest extends AbstractRequest {
	private Hashtable<Integer, Integer> mKeyRegister;
	
	public KeyRequest(){
		mData = ByteBuffer.allocate(8);
		mData.put(STX);
		mData.put((byte) 0x07);
		mData.putInt(0);
		mData.put((byte) 0);
		mData.put(ETX);
		fillKeyRegister();
	}
	
	public void setData(int keyCode, byte state){
		Integer convertKeyCode = mKeyRegister.get(keyCode);
		if(convertKeyCode == null){
			Log.e("KeyRequest", "Keycode not found in register");
		}
		mData.putInt(2, convertKeyCode);
		mData.put(6, state);
	}
	
	private void fillKeyRegister(){
		mKeyRegister = new Hashtable<Integer, Integer>();
		//Numbers
		mKeyRegister.put(KeyEvent.KEYCODE_0, 48);
		mKeyRegister.put(KeyEvent.KEYCODE_1, 49);
		mKeyRegister.put(KeyEvent.KEYCODE_2, 50);
		mKeyRegister.put(KeyEvent.KEYCODE_3, 51);
		mKeyRegister.put(KeyEvent.KEYCODE_4, 52);
		mKeyRegister.put(KeyEvent.KEYCODE_5, 53);
		mKeyRegister.put(KeyEvent.KEYCODE_6, 54);
		mKeyRegister.put(KeyEvent.KEYCODE_7, 55);
		mKeyRegister.put(KeyEvent.KEYCODE_8, 56);
		mKeyRegister.put(KeyEvent.KEYCODE_9, 57);
		//Letters
		mKeyRegister.put(KeyEvent.KEYCODE_A, 65);
		mKeyRegister.put(KeyEvent.KEYCODE_B, 66);
		mKeyRegister.put(KeyEvent.KEYCODE_C, 67);
		mKeyRegister.put(KeyEvent.KEYCODE_D, 68);
		mKeyRegister.put(KeyEvent.KEYCODE_E, 69);
		mKeyRegister.put(KeyEvent.KEYCODE_F, 70);
		mKeyRegister.put(KeyEvent.KEYCODE_G, 71);
		mKeyRegister.put(KeyEvent.KEYCODE_H, 72);
		mKeyRegister.put(KeyEvent.KEYCODE_I, 73);
		mKeyRegister.put(KeyEvent.KEYCODE_J, 74);
		mKeyRegister.put(KeyEvent.KEYCODE_K, 75);
		mKeyRegister.put(KeyEvent.KEYCODE_L, 76);
		mKeyRegister.put(KeyEvent.KEYCODE_M, 77);
		mKeyRegister.put(KeyEvent.KEYCODE_N, 78);
		mKeyRegister.put(KeyEvent.KEYCODE_O, 79);
		mKeyRegister.put(KeyEvent.KEYCODE_P, 80);
		mKeyRegister.put(KeyEvent.KEYCODE_Q, 81);
		mKeyRegister.put(KeyEvent.KEYCODE_R, 82);
		mKeyRegister.put(KeyEvent.KEYCODE_S, 83);
		mKeyRegister.put(KeyEvent.KEYCODE_T, 84);
		mKeyRegister.put(KeyEvent.KEYCODE_U, 85);
		mKeyRegister.put(KeyEvent.KEYCODE_V, 86);
		mKeyRegister.put(KeyEvent.KEYCODE_W, 87);
		mKeyRegister.put(KeyEvent.KEYCODE_X, 88);
		mKeyRegister.put(KeyEvent.KEYCODE_Y, 89);
		mKeyRegister.put(KeyEvent.KEYCODE_Z, 90);
		//Numpad
		mKeyRegister.put(KeyEvent.KEYCODE_NUMPAD_0, 96);
		mKeyRegister.put(KeyEvent.KEYCODE_NUMPAD_1, 97);
		mKeyRegister.put(KeyEvent.KEYCODE_NUMPAD_2, 98);
		mKeyRegister.put(KeyEvent.KEYCODE_NUMPAD_3, 99);
		mKeyRegister.put(KeyEvent.KEYCODE_NUMPAD_4, 100);
		mKeyRegister.put(KeyEvent.KEYCODE_NUMPAD_5, 101);
		mKeyRegister.put(KeyEvent.KEYCODE_NUMPAD_6, 102);
		mKeyRegister.put(KeyEvent.KEYCODE_NUMPAD_7, 103);
		mKeyRegister.put(KeyEvent.KEYCODE_NUMPAD_8, 104);
		mKeyRegister.put(KeyEvent.KEYCODE_NUMPAD_9, 105);
		//Fxx
		mKeyRegister.put(KeyEvent.KEYCODE_F1, 112);
		mKeyRegister.put(KeyEvent.KEYCODE_F2, 113);
		mKeyRegister.put(KeyEvent.KEYCODE_F3, 114);
		mKeyRegister.put(KeyEvent.KEYCODE_F4, 115);
		mKeyRegister.put(KeyEvent.KEYCODE_F5, 116);
		mKeyRegister.put(KeyEvent.KEYCODE_F6, 117);
		mKeyRegister.put(KeyEvent.KEYCODE_F7, 118);
		mKeyRegister.put(KeyEvent.KEYCODE_F8, 119);
		mKeyRegister.put(KeyEvent.KEYCODE_F9, 120);
		mKeyRegister.put(KeyEvent.KEYCODE_F10, 121);
		mKeyRegister.put(KeyEvent.KEYCODE_F11, 122);
		mKeyRegister.put(KeyEvent.KEYCODE_F12, 123);
		//Stuff
		mKeyRegister.put(KeyEvent.KEYCODE_TAB, 9);
		mKeyRegister.put(KeyEvent.KEYCODE_ALT_LEFT, 18);
		//Keypad
		//??
		
		//TODO...
		
		
//		public static final int	KEY_FIRST	400
//		public static final int	KEY_LAST	402
//		public static final int	KEY_LOCATION_LEFT	2
//		public static final int	KEY_LOCATION_NUMPAD	4
//		public static final int	KEY_LOCATION_RIGHT	3
//		public static final int	KEY_LOCATION_STANDARD	1
//		public static final int	KEY_LOCATION_UNKNOWN	0
//		public static final int	KEY_PRESSED	401
//		public static final int	KEY_RELEASED	402
//		public static final int	KEY_TYPED	400
//		public static final int	VK_ACCEPT	30
//		public static final int	VK_ADD	107
//		public static final int	VK_AGAIN	65481
//		public static final int	VK_ALL_CANDIDATES	256
//		public static final int	VK_ALPHANUMERIC	240
//		public static final int	VK_ALT_GRAPH	65406
//		public static final int	VK_AMPERSAND	150
//		public static final int	VK_ASTERISK	151
//		public static final int	VK_AT	512
//		public static final int	VK_BACK_QUOTE	192
//		public static final int	VK_BACK_SLASH	92
//		public static final int	VK_BACK_SPACE	8
//		public static final int	VK_BRACELEFT	161
//		public static final int	VK_BRACERIGHT	162
//		public static final int	VK_CANCEL	3
//		public static final int	VK_CAPS_LOCK	20
//		public static final int	VK_CIRCUMFLEX	514
//		public static final int	VK_CLEAR	12
//		public static final int	VK_CLOSE_BRACKET	93
//		public static final int	VK_CODE_INPUT	258
//		public static final int	VK_COLON	513
//		public static final int	VK_COMMA	44
//		public static final int	VK_COMPOSE	65312
//		public static final int	VK_CONTROL	17
//		public static final int	VK_CONVERT	28
//		public static final int	VK_COPY	65485
//		public static final int	VK_CUT	65489
//		public static final int	VK_DEAD_ABOVEDOT	134
//		public static final int	VK_DEAD_ABOVERING	136
//		public static final int	VK_DEAD_ACUTE	129
//		public static final int	VK_DEAD_BREVE	133
//		public static final int	VK_DEAD_CARON	138
//		public static final int	VK_DEAD_CEDILLA	139
//		public static final int	VK_DEAD_CIRCUMFLEX	130
//		public static final int	VK_DEAD_DIAERESIS	135
//		public static final int	VK_DEAD_DOUBLEACUTE	137
//		public static final int	VK_DEAD_GRAVE	128
//		public static final int	VK_DEAD_IOTA	141
//		public static final int	VK_DEAD_MACRON	132
//		public static final int	VK_DEAD_OGONEK	140
//		public static final int	VK_DEAD_SEMIVOICED_SOUND	143
//		public static final int	VK_DEAD_TILDE	131
//		public static final int	VK_DEAD_VOICED_SOUND	142
//		public static final int	VK_DECIMAL	110
//		public static final int	VK_DELETE	127
//		public static final int	VK_DIVIDE	111
//		public static final int	VK_DOLLAR	515
//		public static final int	VK_DOWN	40
//		public static final int	VK_END	35
//		public static final int	VK_ENTER	10
//		public static final int	VK_EQUALS	61
//		public static final int	VK_ESCAPE	27
//		public static final int	VK_EURO_SIGN	516
//		public static final int	VK_EXCLAMATION_MARK	517
//		public static final int	VK_FINAL	24
//		public static final int	VK_FIND	65488
//		public static final int	VK_FULL_WIDTH	243
//		public static final int	VK_GREATER	160
//		public static final int	VK_HALF_WIDTH	244
//		public static final int	VK_HELP	156
//		public static final int	VK_HIRAGANA	242
//		public static final int	VK_HOME	36
//		public static final int	VK_INPUT_METHOD_ON_OFF	263
//		public static final int	VK_INSERT	155
//		public static final int	VK_INVERTED_EXCLAMATION_MARK	518
//		public static final int	VK_JAPANESE_HIRAGANA	260
//		public static final int	VK_JAPANESE_KATAKANA	259
//		public static final int	VK_JAPANESE_ROMAN	261
//		public static final int	VK_KANA	21
//		public static final int	VK_KANA_LOCK	262
//		public static final int	VK_KANJI	25
//		public static final int	VK_KATAKANA	241
//		public static final int	VK_KP_DOWN	225
//		public static final int	VK_KP_LEFT	226
//		public static final int	VK_KP_RIGHT	227
//		public static final int	VK_KP_UP	224
//		public static final int	VK_LEFT	37
//		public static final int	VK_LEFT_PARENTHESIS	519
//		public static final int	VK_LESS	153
//		public static final int	VK_META	157
//		public static final int	VK_MINUS	45
//		public static final int	VK_MODECHANGE	31
//		public static final int	VK_MULTIPLY	106
//		public static final int	VK_NONCONVERT	29
//		public static final int	VK_NUM_LOCK	144
//		public static final int	VK_NUMBER_SIGN	520
//		public static final int	VK_OPEN_BRACKET	91
//		public static final int	VK_PAGE_DOWN	34
//		public static final int	VK_PAGE_UP	33
//		public static final int	VK_PASTE	65487
//		public static final int	VK_PAUSE	19
//		public static final int	VK_PERIOD	46
//		public static final int	VK_PLUS	521
//		public static final int	VK_PREVIOUS_CANDIDATE	257
//		public static final int	VK_PRINTSCREEN	154
//		public static final int	VK_PROPS	65482
//		public static final int	VK_QUOTE	222
//		public static final int	VK_QUOTEDBL	152
//		public static final int	VK_RIGHT	39
//		public static final int	VK_RIGHT_PARENTHESIS	522
//		public static final int	VK_ROMAN_CHARACTERS	245
//		public static final int	VK_SCROLL_LOCK	145
//		public static final int	VK_SEMICOLON	59
//		public static final int	VK_SEPARATER	108
//		public static final int	VK_SEPARATOR	108
//		public static final int	VK_SHIFT	16
//		public static final int	VK_SLASH	47
//		public static final int	VK_SPACE	32
//		public static final int	VK_STOP	65480
//		public static final int	VK_SUBTRACT	109
//		public static final int	VK_UNDEFINED	0
//		public static final int	VK_UNDERSCORE	523
//		public static final int	VK_UNDO	65483
//		public static final int	VK_UP	38

	}
	
}
