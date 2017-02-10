package cat.spring;

/**
 * Created by Administrator on 2017/1/26.
 */
public class ApplicationContextTest {
    public static void main(String args[]){
                  for(int i=0;i<10000;i++){
                      if((i%2==1)&&(i%3==0)&&(i%4==1)&&(i%5==4)&&(i%6==3)&&(i%7==0)&&(i%8==1)&&(i%9==0)){
                          System.out.print(i);
                      }
                  }
    }
}
