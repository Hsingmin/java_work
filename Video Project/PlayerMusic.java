package util;

import java.net.URL;
import javax.media.*;

/*播放网络媒体的程序*/
public class PlayerMusic extends javax.media.ControllerAdapter{
    
    private Player player = null;//播放器核心对象
    
    public static void main(String[] args){
        new PlayerMusic("file:D:\\workspace\\test\\WebRoot\\test.mp3");
        //这里改成http://localhost:8080/test/test.mp3就会出错
    }
    
    public PlayerMusic(String address){
        try{
            //通过API创建Player对象,是最简单的办法,速度是最慢的.
            player = Manager.createPlayer(new URL(address));
            //放入控制器监听者(自己)
            player.addControllerListener(this);
            //开始检查数据,根据情况会自动调用复写的ControllerAdapter中的函数
            player.realize();
        }
        catch(Exception e){
            e.printStackTrace();
            this.deallocate();
        }
    }
    
    /**
     * 释放连接
     */    
    public void deallocate(){
        if(player != null){
            System.out.println ("error");
            player.removeControllerListener(this);//移除控制器监听者(自己,因为继承了ControllerAdapter)
            player.close();//释放资源
            player = null;//一般好的程序都要释放指针
        }
    }
    
    
    
    /*** 以下是复写超类ControllerAdapter实配器的函数 ***/
    
    
    /**
     * Player数据编码检查完毕的回调函数(一般用于通知本对象可以播放了,所以player.start()函数在这里调用)
     */
    public void realizeComplete(RealizeCompleteEvent e){
        player.start();
    }
    
    /**
     * Player正常播放完毕的回调函数(一般用于通知本对象释放资源)
     */
    public void endOfMedia(EndOfMediaEvent e) {this.deallocate();}
    
    /**
     * Player遇到错误后的回调函数(一般用于通知本对象释放资源)
     */
    public void audioDeviceUnavailable(AudioDeviceUnavailableEvent e) {this.deallocate();}
    public void connectionError(ConnectionErrorEvent e) {this.deallocate();}
    public void internalError(InternalErrorEvent e) {this.deallocate();}
    public void dataLostError(DataLostErrorEvent e) {this.deallocate();}
    public void controllerError(ControllerErrorEvent e) {this.deallocate();}
}
