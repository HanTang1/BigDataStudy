/**
 * @Author: HanTang
 * @Description:
 * @Date: Created in 下午10:23 18/8/25}
 */
public class B extends A {
    int price =20;

    @Override
    public void eat() {

        System.out.println("B");
    }

    public static void main(String[] args){
        A a = new B();
        a.eat();
        System.out.println(a.price);
    }
}
