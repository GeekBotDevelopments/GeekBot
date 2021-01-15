package bot.c_Conversion;

import com.google.protobuf.Struct;
import com.google.protobuf.UInt32Value;

public class emu6502 {
    int DEBUG = 2;

    static int  MAXMEMORY   = 65536;
    static int  RAMSIZE     = 32768;
    static byte RAMSTART    = (byte)0x0000;
    static int  ROMSIZE     = 32768;
    static byte ROMSTART    = (byte)0x8000;
    static int  TOTALMEMORY = RAMSIZE + ROMSIZE;

    byte VEC_NMI   = (byte)0xfffa;
    byte VEC_RESET = (byte)0xfffc;
    byte VEC_IRQ   = (byte)0xfffe;

    Registers registers;

    void AllMemory() {
        //byte[RAMSIZE] ram;
        //byte[ROMSIZE] rom;
        byte[] all;
    }

    void ProgramCounter(){
        
    }

    public emu6502() {

    }
}
