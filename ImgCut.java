import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
//
public class ImgCut {
    // 文件  所在目录
    static String path = "C:\\Users\\Administrator\\Desktop\\test\\";
    public static void main(String[] args) throws Exception { // main方法测试用
        ImgCut t = new ImgCut();
        t.readUsingImageReader(path + "jietu2.jpeg");
    }
    public void readUsingImageReader(String src)
            throws Exception {
        // 取得图片读入器
        Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = (ImageReader) readers.next();
        // 取得图片读入流
        InputStream source = new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        // 图片参数
        ImageReadParam param = reader.getDefaultReadParam();
         //自定义画布 这里采用双倍 截取的白条 重复两次
        BufferedImage combined = new BufferedImage(422, 8 * 26*2, BufferedImage.TYPE_INT_RGB);
        //设置画笔
        Graphics g = combined.getGraphics();
        // 100，200是左上起始位置，300就是取宽度为300的，就是从100开始取300宽，就是横向100~400，同理纵向200~350的区域就取高度150
        // Rectangle rect = new Rectangle(100, 200, 300, 150);
       // 黑边和白边长度和为 15  每次+15
        int h=0;
        for (int i = 0; i < 26; i++) {
            // 截取 白条
            Rectangle rect = new Rectangle(330, 945 + 15 *i, 422, 8);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            //截取 到的白条 写入文件
            File f=new File(path + i + ".jpg");
            ImageIO.write(bi, "jpg", f);
            //读取刚刚截取的 白条 写入自定义的 画布
            BufferedImage image1 = ImageIO.read(f);
            //重复两次 白条写入 画布
            g.drawImage(image1, 0, h, null);
            g.drawImage(image1, 0, h+8, null);
            h+=16;
        }
        //保存自定义的画布
        ImageIO.write(combined, "jpg", new File("new.jpg"));
    }

}
