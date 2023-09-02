import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class ImageEditor{

    public static void main(String []args){

        Scanner sc = new Scanner(System.in);
        System.out.println("~ Enter mirror_image for mirroring image : ");
        System.out.println("~ Enter gray_image for making image to black and white mode : ");
        System.out.println("~ Enter contrast_change for increasing or decreasing the contrast of image : ");
        System.out.println("~ Enter rotate_image for rotating the image to the right side (90 degrees) : ");
        System.out.println("~ Enter  brightness_control for increasing or decreasing the brightness of the image : ");
        String[] input = new String[3];
        for (int i=0; i<3; i++) {
            if(i==1)
            {
                System.out.println("Now enter the name of the image you want to perform the operation : ");
            }
            else if(i==2)
            {
                System.out.println("Now enter the name of the newly formed image without the extension : ");
            }
            input[i] = sc.nextLine();
        }
        String[] cmds = {"mirror_image","gray_image","contrast_change","rotate_image","brightness_control"};
        String cmd = "";
        boolean cmdexists = false;
           for(int i=0; i < cmds.length; i++){

            if(cmds[i].equals(input[0])){
                cmdexists = true;
                cmd = cmds[i];
                break;
            }
        }
        if(!cmdexists){
            System.out.printf("%s : not a valid command !\n",input[0]);
            System.out.println("Possible Commands : mirror_image , gray_image , contrast_change , rotate_image , brightness_control");
            System.exit(1);
        }
        if(!input[1].contains(".jpg")){
            System.out.println("[argument] : only .jpg files supported !");
            System.exit(1);
        }
        File outputImage = new File(input[2]+".jpg");
        try {
            File inputFile = new File(input[1]);
            BufferedImage inputImage = ImageIO.read(inputFile);
            switch(cmd){
                case "mirror_image":
                    ImageIO.write(mirrorImage(inputImage), "jpg", outputImage);
                    break;

                case "gray_image":
                    ImageIO.write(convert_to_grayscale(inputImage), "jpg", outputImage);
                    break;

                case "contrast_change":
                    ImageIO.write(convert_to_monochrome(inputImage), "jpg", outputImage);
                    break;

                case "rotate_image":
                    ImageIO.write(rotateImage(inputImage), "jpg", outputImage);
                    break;

                case "brightness_control":
                    System.out.println("Enter the percentage of change in brightness (increase/decrease) (in digits eg ~ 10 for 10%):");
                    int percent = sc.nextInt();
                    System.out.println("");
                    ImageIO.write(change_brightness(inputImage, percent), "jpg", outputImage);
                    break;

                default:
                    System.out.println("Some error ocurred !");
                    break;

            }
            System.out.printf("Final output image %s created.\n",outputImage);
            System.exit(1);
        } catch (Exception e) {
            System.out.println(e + "File not Found !");
        }
        System.exit(1);
    }

    public static BufferedImage mirrorImage(BufferedImage image)
    {
        int height = image.getHeight();
        int width = image.getWidth();
        BufferedImage imgOut = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for(int i = 0 ; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                imgOut.setRGB(width-1-j,i,image.getRGB(j,i));
                imgOut.setRGB(j,i,image.getRGB(width-1-j,i));

            }
        }
        return imgOut;
    }
    public static BufferedImage rotateImage(BufferedImage image)
    {
        int height = image.getHeight();
        int width = image.getWidth();
        BufferedImage imgOut = new BufferedImage(height, width, BufferedImage.TYPE_3BYTE_BGR);

        for(int i = 0 ; i < height; i++)
        {
            for(int j = 0; j < width ; j++)
            {
                imgOut.setRGB(i,j,image.getRGB(width-j-1, i));
            }
        }
        imgOut = mirrorImage(imgOut);
        return imgOut;
    }
    public static BufferedImage convert_to_grayscale(BufferedImage image)
    {
        int height = image.getHeight();
        int width = image.getWidth();
        BufferedImage imgOut = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for(int i = 0 ; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                imgOut.setRGB(i,j,image.getRGB(i,j));
            }
        }
        return imgOut;

    }

    public static BufferedImage convert_to_monochrome(BufferedImage image){

        int height = image.getHeight();
        int width = image.getWidth();
        BufferedImage imgOut = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for(int i = 0 ; i < height; i++){
            for(int j = 0; j < width; j++){
                int rgb = image.getRGB(j, i);
                Color color = new Color(rgb);
                int red = color.getRed();
                int blue = color.getBlue();
                int green = color.getGreen();
                int avgCol = (int)(Math.ceil((red + green + blue)/3.0));
                int bgr = new Color(avgCol,avgCol,avgCol).getRGB();
                imgOut.setRGB(j,i,bgr);

            }
        }
        return imgOut;

    }

    public static BufferedImage change_brightness(BufferedImage image, double percent){

        int height = image.getHeight();
        int width = image.getWidth();

        BufferedImage imgOut = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        for(int i = 0 ; i < height; i++){
            for(int j = 0; j < width; j++){

                int rgb = image.getRGB(j, i);

                Color color = new Color(rgb);

                int red = (int)(Math.ceil(color.getRed()*((percent + 100)/100.0)));
                int green = (int)(Math.ceil(color.getGreen()*((percent + 100)/100.0)));
                int blue = (int)(Math.ceil(color.getBlue()*((percent + 100)/100.0)));

                if(red > 255){
                    red = 255;
                }

                if(green > 255 ){
                    green = 255;
                }

                if(blue > 255){
                    blue = 255;
                }

                int bgr = new Color(red,green,blue).getRGB();

                imgOut.setRGB( j, i, bgr);

            }
        }
        return imgOut;

    }

}