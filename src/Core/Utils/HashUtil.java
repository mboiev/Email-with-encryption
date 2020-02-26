package Core.Utils;


public class HashUtil {
    private String hashMessage;
    public HashUtil(String message) {
        this.hashMessage = message;
    }

    public String messageToHash(){
        byte[] byteMessageArray = hashMessage.getBytes();
        StringBuilder binaryString = new StringBuilder();
        for (byte b : byteMessageArray)
        {
            int val = b;
            for (int i = 0; i < 8; i++)
            {
                binaryString.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        int h0 = 0x6A09E667;
        int h1 = 0xBB67AE85;
        int h2 = 0x3C6EF372;
        int h3 = 0xA54FF53A;
        int h4 = 0x510E527F;
        int h5 = 0x9B05688C;
        int h6 = 0x1F83D9AB;
        int h7 = 0x5BE0CD19;

        int[] constTable = new int[] {
            0x428A2F98, 0x71374491, 0xB5C0FBCF, 0xE9B5DBA5, 0x3956C25B, 0x59F111F1, 0x923F82A4, 0xAB1C5ED5,
            0xD807AA98, 0x12835B01, 0x243185BE, 0x550C7DC3, 0x72BE5D74, 0x80DEB1FE, 0x9BDC06A7, 0xC19BF174,
            0xE49B69C1, 0xEFBE4786, 0x0FC19DC6, 0x240CA1CC, 0x2DE92C6F, 0x4A7484AA, 0x5CB0A9DC, 0x76F988DA,
            0x983E5152, 0xA831C66D, 0xB00327C8, 0xBF597FC7, 0xC6E00BF3, 0xD5A79147, 0x06CA6351, 0x14292967,
            0x27B70A85, 0x2E1B2138, 0x4D2C6DFC, 0x53380D13, 0x650A7354, 0x766A0ABB, 0x81C2C92E, 0x92722C85,
            0xA2BFE8A1, 0xA81A664B, 0xC24B8B70, 0xC76C51A3, 0xD192E819, 0xD6990624, 0xF40E3585, 0x106AA070,
            0x19A4C116, 0x1E376C08, 0x2748774C, 0x34B0BCB5, 0x391C0CB3, 0x4ED8AA4A, 0x5B9CCA4F, 0x682E6FF3,
            0x748F82EE, 0x78A5636F, 0x84C87814, 0x8CC70208, 0x90BEFFFA, 0xA4506CEB, 0xBEF9A3F7, 0xC67178F2

        };
        int length = binaryString.length();
        int k = kFinder(length);
        binaryString.append("1");
        for(int i = 0; i<k;i++){
            binaryString.append("0");
        }
        String tempstring = "";
        for(int i = 0; i< Long.numberOfLeadingZeros(length);i++){
            tempstring +="0";
        }
        tempstring+=Long.toUnsignedString(length,2);
        binaryString.append(tempstring);
        String[] chunkArray = binaryString.toString().split("(?<=\\G.{512})");
        for(String chunk : chunkArray){
            String[] chunkWords = chunk.split("(?<=\\G.{32})");
            String[] finalChunkWords = new String[64];
            for(int i = 0;i<16;i++){
                finalChunkWords[i] = chunkWords[i];
            }
            for(int i = 16; i<64; i++){
                int s0 = (rotateRight(Integer.parseUnsignedInt(finalChunkWords[i-15],2) , 7))^
                        (rotateRight(Integer.parseUnsignedInt(finalChunkWords[i-15],2),18))^
                        (Integer.parseUnsignedInt(finalChunkWords[i-15],2)>>>3);
                int s1 = (rotateRight(Integer.parseUnsignedInt(finalChunkWords[i-2],2),17))^
                        (rotateRight(Integer.parseUnsignedInt(finalChunkWords[i-2],2),19))^
                        (Integer.parseUnsignedInt(finalChunkWords[i-2],2)>>>10);
                int w = Integer.parseUnsignedInt(finalChunkWords[i-16],2)+
                        s0+Integer.parseUnsignedInt(finalChunkWords[i-7],2)+s1;
                tempstring = "";
                for (int c = 0; c < Integer.numberOfLeadingZeros(w); c++){
                    tempstring+="0";
                }
                tempstring+= Integer.toUnsignedString(w,2);
                finalChunkWords[i] = tempstring;
            }
            int a = h0;
            int b = h1;
            int c = h2;
            int d = h3;
            int e = h4;
            int f = h5;
            int g = h6;
            int h = h7;

            for(int i = 0; i < 64; i++){
                int sg0 = (rotateRight(a,2))^(rotateRight(a,13))^(rotateRight(a,22));
                int ma = (a&b)^(a&c)^(b&c);
                int t2 = sg0+ma;
                int sg1 = (rotateRight(e,6))^(rotateRight(e,11))^(rotateRight(e,25));
                int ch = (e&f)^((~e)&g);
                int t1 = h+sg1+ch+constTable[i]+Integer.parseUnsignedInt(finalChunkWords[i],2);

                h = g;
                g = f;
                f = e;
                e = d + t1;
                d = c;
                c = b;
                b = a;
                a = t1 + t2;
            }
            h0 = h0 + a;
            h1 = h1 + b;
            h2 = h2 + c;
            h3 = h3 + d;
            h4 = h4 + e;
            h5 = h5 + f;
            h6 = h6 + g;
            h7 = h7 + h;
        }
        String hash = "";
        hash+= String.format("%x",h0);
        hash+= String.format("%x",h1);
        hash+= String.format("%x",h2);
        hash+= String.format("%x",h3);
        hash+= String.format("%x",h4);
        hash+= String.format("%x",h5);
        hash+= String.format("%x",h6);
        hash+= String.format("%x",h7);
        return hash;
    }


    private static int kFinder(int length){
        int result = 0;
        for(int i = 0;i<Integer.MAX_VALUE;i++){
            if((length + 1 + i + 64) % 512 == 0 ){
                result = i;
                break;
            }
        }
        return result;
    }

    private Integer rotateRight(int n, int d){
        return (n >>> d) | (n << (32 - d));
    }
}
