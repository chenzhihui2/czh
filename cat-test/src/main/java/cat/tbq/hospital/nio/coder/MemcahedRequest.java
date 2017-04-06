package cat.tbq.hospital.nio.coder;

import lombok.Data;

import java.util.Random;

/**
 * Created by Administrator on 2016/12/29.
 */

/**
 *
  Magic 0    0x80 for requests 0x81 for responses
  OpCode 1   0x01...0x1A
  Key length 2 and 3    1... 32,767
 Extra length 4  0x00, x04 or 0x08
 Data type 5  0x00
 Reserved 6 and 7  0x00
 Total body length 8 - 11  Total size of body inclusive extras
 Opaque 12 - 15 Any signed 32 bit integer, this one will be also included in the response and so make it easier to map requests to responses.
 CAS  16 - 23  Data version check
 */
@Data
public class MemcahedRequest {
    private static final Random rand = new Random();
    private int magic = 0x80;//fixed so hard coded
    private byte opCode;// the operation e.g. set or get
    private String key;//the key to delete,get or set
    private int flags = 0xdeadbeef;//random
    private int expires;//0=item never expires
    private String body;//if opCode is set,the value
    private int id = rand.nextInt();//Opaque
    private long cas;//data version check ...not used
    private boolean hasExtras;//not all ops have extras

    public MemcahedRequest(byte opcode, String key, String value) {
        this.opCode = opcode;
        this.key = key;
        this.body = value == null ? "" : value;
        hasExtras=opcode==Opcode.SET;
    }
    public MemcahedRequest(byte opcode, String key) {
        this(opcode,key,null);
    }

}
