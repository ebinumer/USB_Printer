package com.ebinumer.usbprinter;

public class PrinterCommands {
    public static final byte HT = 0x9;
    public static final byte LF = 0x0A;
    public static final byte CR = 0x0D;
    public static final byte ESC = 0x1B;
    public static final byte DLE = 0x10;
    public static final byte GS = 0x1D;
    public static final byte FS = 0x1C;
    public static final byte STX = 0x02;
    public static final byte US = 0x1F;
    public static final byte CAN = 0x18;
    public static final byte CLR = 0x0C;
    public static final byte EOT = 0x04;

    public static final byte[] INIT = {27, 64};
    public static byte[] FEED_LINE = {10};
    public static byte[] QUANTITY_LINE = {2};

    public static byte[] SELECT_FONT_A = {20, 33, 0};

    public static byte[] SET_BAR_CODE_HEIGHT = {29, 104, 100};
    public static byte[] PRINT_BAR_CODE_1 = {29, 107, 2};
    public static byte[] PRINT_BAR_CODE_2 = {0x1b, 0x1d, 0x61, 0x01};
    public static byte[] PRINT_BAR_CODE_3 = {0x1b, 0x62, 0x06, 0x02, 0x02};

    public static byte[] SEND_NULL_BYTE = {0x00};

    public static byte[] SELECT_PRINT_SHEET = {0x1B, 0x63, 0x30, 0x02};


    public static byte[] FEED_PAPER_AND_CUT1 = {0x1D, 0x56, 66, 0x00};
    public static byte[] FEED_PAPER_AND_CUT2 = {0x1D, 0x56, 48, 0x00};
    public static byte[] FEED_PAPER_AND_CUT3 = { 29, 'V', 65, 0 };
    public static byte[] FEED_PAPER_AND_CUT4 = { 0x1d, 0x56, 0x42, 0x00 };
    public static byte[]  FEED_PAPER_AND_CUT5 = new byte[]{27, 109};
    public static byte[] FEED_PAPER_AND_CUT6 =  {0x1d,0x56,0x00};
    public static byte[] FEED_PAPER_AND_CUT7 =  {0x1d, 'V', 1};
    public static byte[] FEED_PAPER_AND_CUT8 =  {0x1d, 0x56, 0x41, 0x0A};
    public static byte[] FEED_PAPER_AND_CUT9 =  {0x1d, 0x65, 0x03, 0x0c};

    public static byte[] FEED_PAPER_AND_CUT_NEW = new byte[]{29, 86, (byte)66, (byte)1};


    public static byte[] SELECT_CYRILLIC_CHARACTER_CODE_TABLE = {0x1B, 0x74, 0x11};

    public static byte[] SELECT_BIT_IMAGE_MODE = {0x1B, 0x2A, 33, -128, 0};
    public static byte[] SET_LINE_SPACING_24 = {0x1B, 0x33, 24};
    public static byte[] SET_LINE_SPACING_30 = {0x1B, 0x33, 30};

    public static byte[] TRANSMIT_DLE_PRINTER_STATUS = {0x10, 0x04, 0x01};
    public static byte[] TRANSMIT_DLE_OFFLINE_PRINTER_STATUS = {0x10, 0x04, 0x02};
    public static byte[] TRANSMIT_DLE_ERROR_STATUS = {0x10, 0x04, 0x03};
    public static byte[] TRANSMIT_DLE_ROLL_PAPER_SENSOR_STATUS = {0x10, 0x04, 0x04};

    public static final byte[] ESC_FONT_COLOR_DEFAULT = new byte[] { 0x1B, 'r',0x00 };
    public static final byte[] FS_FONT_ALIGN = new byte[] { 0x1C, 0x21, 1, 0x1B, 0x21, 1 };
    public static final byte[] ESC_ALIGN_LEFT = new byte[] { 0x1b, 'a', 0x00 };
    public static final byte[] ESC_ALIGN_RIGHT = new byte[] { 0x1b, 'a', 0x02 };
    public static final byte[] ESC_ALIGN_CENTER = new byte[] { 0x1b, 'a', 0x01 };
    public static final byte[] ESC_CANCEL_BOLD = new byte[] { 0x1B, 0x45, 0 };


    /*********************************************/
    public static final byte[] ESC_HORIZONTAL_CENTERS = new byte[] { 0x1B, 0x44, 20, 28, 00 };
    public static final byte[] ESC_CANCLE_HORIZONTAL_CENTERS = new byte[] { 0x1B, 0x44, 00 };
    /*********************************************/

    public static final byte[] ESC_ENTER = new byte[] { 0x1B, 0x4A, 0x40 };
    public static final byte[] PRINTE_TEST = new byte[] { 0x1D, 0x28, 0x41 };

    //CASH DRAWER
    public static final byte[] CASH_DRAWER_OPEN1 = new byte[] { 27, 112, 0, 60, 120 };
    public static final byte[] CASH_DRAWER_OPEN2 = new byte[] { 27, 112, 0, 100, (byte) 250 };
    public static final byte[] CASH_DRAWER_OPEN3 = new byte[] { 27, 112, 48, 55, 121 };

    // Epson TM-T88        (ESC p m t1 t2 -  drawer kick command)
    public static final byte[] CASH_DRAWER_OPEN  = new byte[] { 27, 112, 0, 25, (byte) 250 };



}
