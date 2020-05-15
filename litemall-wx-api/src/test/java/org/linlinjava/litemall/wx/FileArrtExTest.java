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
//        Path path = new File("E:\\temp\\doc\\ss1.txt").toPath();
//        readAndDisplayMetadata("E:\\temp\\picture\\a.png");

        poiTest();
/*        try{
            Files.setAttribute(path, "user:aaa", "123456".getBytes());
        }catch(IOException e){
            System.out.println("error");
            System.out.println(e.getMessage());
        }
        try{
            System.out.println(Files.getAttribute(path, "basic:aaa"));
        }catch(IOException e){
            System.out.println(e.getMessage());
        }*/

        /*try {
            // 写入一个属性
            UserDefinedFileAttributeView userDefinedFileAttributeView = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);

*//*            BasicFileAttributeView basicFileAttributeView = Files.getFileAttributeView(path, BasicFileAttributeView.class);
            BasicFileAttributes basicFileAttributes = basicFileAttributeView.readAttributes();
            System.out.println("创建时间 : " + new Date(basicFileAttributes.creationTime().toMillis()));
            basicFileAttributes.fileKey();
            DosFileAttributeView dosFileAttributeView = Files.getFileAttributeView(path, DosFileAttributeView.class);
            dosFileAttributeView.setHidden(true);*//*

            FileOwnerAttributeView foav = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
            UserPrincipal owner = foav.getOwner();
            System.out.format("Original owner  of  %s  is %s%n", path,
                    owner.getName());

            FileSystem fs = FileSystems.getDefault();
            UserPrincipalLookupService upls = fs.getUserPrincipalLookupService();

            UserPrincipal newOwner = upls.lookupPrincipalByName("yliyun");
            foav.setOwner(newOwner);

            UserPrincipal changedOwner = foav.getOwner();
            System.out.format("New owner  of  %s  is %s%n", path,
                    changedOwner.getName());

//            aclView.setAcl();
//            userDefinedFileAttributeView.write("版权人", Charset.defaultCharset().encode("abcdefg"));

*//*            List<String> attrNames = userDefinedFileAttributeView.list(); // 读出所有属性
            for (String name : attrNames) {
                ByteBuffer bb = ByteBuffer.allocate(userDefinedFileAttributeView.size(name)); // 准备一块儿内存块读取
                userDefinedFileAttributeView.read(name, bb);
                bb.flip();
                String value = Charset.defaultCharset().decode(bb).toString();
                System.out.println(name + " : " + value);

            }

            UserDefinedFileAttributeView view = Files
                    .getFileAttributeView(path,UserDefinedFileAttributeView.class);
            String name = "user.mimetype";
            ByteBuffer buf = ByteBuffer.allocate(view.size(name));
            view.read(name, buf);
            buf.flip();
            String value = Charset.defaultCharset().decode(buf).toString();*//*
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }*/
    }

    static void readAndDisplayMetadata( String fileName ) {
        try {

            File file = new File( fileName );
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

            if (readers.hasNext()) {

                // pick the first available ImageReader
                ImageReader reader = readers.next();

                // attach source to the reader
                reader.setInput(iis, true);

                // read metadata of first image
                IIOMetadata metadata = reader.getImageMetadata(0);
                String[] names = metadata.getMetadataFormatNames();
                int length = names.length;
                for (int i = 0; i < length; i++) {
                    System.out.println( "Format name: " + names[ i ] );
//                    displayMetadata(metadata.getAsTree(names[i]));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void poiTest() throws FileNotFoundException, IOException, NoPropertySetStreamException,
            MarkUnsupportedException, UnexpectedPropertySetTypeException {
        try (
                final FileInputStream fs = new FileInputStream("E:\\\\temp\\\\doc\\2water.doc");
                final POIFSFileSystem poifs = new POIFSFileSystem(fs)) {
            final DirectoryEntry dir = poifs.getRoot();
            final DocumentEntry siEntry = (DocumentEntry) dir.getEntry(SummaryInformation.DEFAULT_STREAM_NAME);
            try (final DocumentInputStream dis = new DocumentInputStream(siEntry)) {
                final PropertySet ps = new PropertySet(dis);
                final SummaryInformation si = new SummaryInformation(ps);
                // Read word doc (not docx) metadata.
//                si.setApplicationName("sdfd");
                System.out.println(si.getApplicationName());
                System.out.println(si.getLastAuthor());
                System.out.println(si.getAuthor());
                System.out.println(si.getKeywords());
                System.out.println(si.getSubject());
                // ...
            }
        }
    }
}
