package org.linlinjava.litemall.wx;

import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
import org.apache.poi.hpsf.*;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/12 18:46
 * @description：TODO
 */
public class FileArrtExTest {
    public static void main(String[] args) throws Exception  {
        Path path = new File("E:\\\\temp\\\\doc\\\\test.xlsx").toPath();

//        path = new File("C:\\Users\\yliyun\\Documents\\WeChat Files\\alceb012\\FileStorage\\File\\2020-05\\test.xlsx").toPath();


        try {
            // 写入一个属性
            UserDefinedFileAttributeView userDefinedFileAttributeView = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);

//            userDefinedFileAttributeView.write("alceb", Charset.defaultCharset().encode("abcdefg"));

            List<String> attrNames = userDefinedFileAttributeView.list(); // 读出所有属性
            for (String name : attrNames) {
                ByteBuffer bb = ByteBuffer.allocate(userDefinedFileAttributeView.size(name)); // 准备一块儿内存块读取
                userDefinedFileAttributeView.read(name, bb);
                bb.flip();
                String value = Charset.defaultCharset().decode(bb).toString();
                System.out.println(name + " : " + value);

            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

/*        Path file = new File("E:\\temp\\doc\\test.xlsx").toPath();
        UserDefinedFileAttributeView view = Files
                .getFileAttributeView(file, UserDefinedFileAttributeView.class);
        view.write("user.mimetype",
                Charset.defaultCharset().encode("text/html"));

        String name = "user.mimetype";
        ByteBuffer buf = ByteBuffer.allocate(view.size(name));
        view.read(name, buf);
        buf.flip();
        String value = Charset.defaultCharset().decode(buf).toString();

        System.out.println(name + " : " + value);*/
    }

}
