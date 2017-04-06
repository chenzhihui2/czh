package cat.tbq.hospital.entity;

import cat.mybatis.BaseBO;
import lombok.Data;

/**
 * Created by czhtbq on 2017/4/6.
 */
@Data
public class UserBO extends BaseBO {
    private String userName;
    private Integer workTerm;
    private String birthday;
    private String startWorkDay;
    private Long phone;
    private Double titleScore;
    private Double titlePoint;
    private Integer education;
    private String jobNumber;

}
