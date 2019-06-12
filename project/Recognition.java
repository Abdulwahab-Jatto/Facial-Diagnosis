
package com.project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Integer.min;
import java.nio.IntBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import org.bytedeco.javacpp.opencv_contrib;
import static org.bytedeco.javacpp.opencv_contrib.createEigenFaceRecognizer;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_core.CV_8U;
import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import static org.bytedeco.javacpp.opencv_core.CV_AA;
import org.bytedeco.javacpp.opencv_core.CvMat;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.NORM_MINMAX;
import static org.bytedeco.javacpp.opencv_core.cvGetSeqElem;
import static org.bytedeco.javacpp.opencv_core.cvPoint;
import static org.bytedeco.javacpp.opencv_core.cvRound;
import static org.bytedeco.javacpp.opencv_core.cvSize;
import org.bytedeco.javacpp.opencv_highgui;
import static org.bytedeco.javacpp.opencv_highgui.CV_CAP_ANY;
import static org.bytedeco.javacpp.opencv_highgui.CV_CAP_PROP_FRAME_HEIGHT;
import static org.bytedeco.javacpp.opencv_highgui.CV_CAP_PROP_FRAME_WIDTH;
import static org.bytedeco.javacpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import org.bytedeco.javacpp.opencv_highgui.CvCapture;
import static org.bytedeco.javacpp.opencv_highgui.cvCreateCameraCapture;
import static org.bytedeco.javacpp.opencv_highgui.cvQueryFrame;
import static org.bytedeco.javacpp.opencv_highgui.cvReleaseCapture;
import static org.bytedeco.javacpp.opencv_highgui.cvWaitKey;
import static org.bytedeco.javacpp.opencv_highgui.imread;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameRecorder;

/**
 *
 * @author User 2
 */
public class Recognition extends javax.swing.JFrame {

    /**
     * Creates new form Registration1^
     */
    public Recognition() throws IOException {
        initComponents();
        
        patxt.setEditable(false);
        }
    
    opencv_contrib.FaceRecognizer facerecog2 = createEigenFaceRecognizer();
        //FaceRecognizer facerecog2 = createLBPHFaceRecognizer();
     
        
    public void writeData(String s, String t) throws IOException {
            BufferedWriter writeval = new BufferedWriter(new FileWriter("recogdata.txt"));
            writeval.write(s+" "+t);
            writeval.close();

        }
    public void stopCam(){
        mythread.runnable = false;
        cvReleaseCapture(capture);
        stabtn.setEnabled(true);
        
    }
    
  private DaemonThread mythread = new DaemonThread();  
  CvCapture capture;
   public void checkLabelDB(int predictedLabel) throws ClassNotFoundException, IOException{
       
       Connection conn = DBConn.dataBase();    
       PreparedStatement ps;
       try{    
       String sql = "SELECT STUDENTDATA.MATRICNUM,STUDENTDATA.FIRSTNAME,STUDENTDATA.LASTNAME,STUDENTDATA.FACULTY,STUDENTDATA.DEPARTMENT,"
               + "STUDENTDATA.ADDRESS,STUDENTDATA.PASSPORT, PATIENTSDATA.PATIENTID FROM STUDENTDATA,PATIENTSDATA WHERE STUDENTDATA.LABEL = ?";
       
            ps=conn.prepareStatement (sql); 
            ps.setInt(1,predictedLabel);
           // ps=conn.prepareStatement (sql); 
            ResultSet rs = ps.executeQuery();
             if (rs.next()){
                 byte[] imagedata =  rs.getBytes("STUDENTDATA.PASSPORT");
                ImageIcon format = new ImageIcon(imagedata);
              Rectangle rec = piclbl.getBounds();
            Image scaledimage = format.getImage().getScaledInstance(rec.width,rec.height,Image.SCALE_DEFAULT);
            format = new ImageIcon(scaledimage); 
            piclbl.setIcon(format);
            fntxt.setText(rs.getString("STUDENTDATA.FIRSTNAME"));
            lntxt.setText(rs.getString("STUDENTDATA.LASTNAME"));
            fatxt.setText(rs.getString("STUDENTDATA.FACULTY"));
            detxt.setText(rs.getString("STUDENTDATA.DEPARTMENT"));
            adtxt.setText(rs.getString("STUDENTDATA.ADDRESS"));
            matxt.setText(rs.getString("STUDENTDATA.MATRICNUM"));
            patxt.setText(rs.getString("PATIENTSDATA.PATIENTID"));
            writeData(patxt.getText(), fntxt.getText());
            stopCam();
             }
       else
                JOptionPane.showMessageDialog(null,"Face not recognized Please register");
         }
       catch(SQLException r){System.out.println(r.getMessage());}
   }
   
  
  
  public static  double getSimilarity(opencv_core.Mat A,opencv_core.Mat B){

  double errorL2 = opencv_core.norm(A,B);
 //errorL2 = opencv_core.norm(A, B);
  
  double similarity = errorL2/(A.cols()* A.rows());
    return similarity;}
     
class DaemonThread implements Runnable{
protected volatile boolean runnable = false;
@Override
@SuppressWarnings({"empty-statement", "UnusedAssignment", "ResultOfObjectAllocationIgnored"})
public void run(){
synchronized (this){
while(runnable){
  
        System.out.println("Working!!!");
	    capture = cvCreateCameraCapture(0);
           opencv_highgui.cvSetCaptureProperty(capture,CV_CAP_PROP_FRAME_WIDTH,640);
            opencv_highgui.cvSetCaptureProperty(capture,CV_CAP_PROP_FRAME_HEIGHT,480);
            
            opencv_core.Mat frame = new opencv_core.Mat();
            Graphics g;
                g = dislbl.getGraphics();
        String fname =  "haarcascade_frontalface_alt2.xml";
        
        opencv_objdetect.CvHaarClassifierCascade fnames = new opencv_objdetect.CvHaarClassifierCascade(opencv_core.cvLoad(fname));
        opencv_core.IplImage image;
        opencv_core.CvMat face_img = new opencv_core.CvMat() ;
            while(true){
                  opencv_core.CvMemStorage faceStorage= opencv_core.CvMemStorage.create();
                  //image = cvQueryFrame(capture1);
                  
                  if(!capture.isNull()){
                      image  = cvQueryFrame(capture);
                    //image = frame.asIplImage();
                  opencv_core.cvFlip(image,image,1);
                    opencv_core.IplImage frame1 = image;
                    opencv_core.cvClearMemStorage(faceStorage);
                    //IplImage image1=IplImage.create(frame1.width(),frame1.height(), IPL_DEPTH_8U, 1);
                    opencv_core.IplImage frame_gray = opencv_core.IplImage.create(frame1.width(),frame1.height(), IPL_DEPTH_8U, 1);
                        opencv_imgproc.cvCvtColor(frame1, frame_gray,opencv_imgproc.CV_BGR2GRAY );
                        opencv_imgproc.cvEqualizeHist(frame_gray, frame_gray);
                        
                        opencv_core.CvSeq faces=opencv_objdetect.cvHaarDetectObjects(frame_gray, fnames, faceStorage, 1.1, 3,opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING,cvSize(80,80),cvSize(250,250));
                        System.out.println(faces.total());
                        
                        for(int i=0;i<faces.total();i++){
                            opencv_core.CvRect r = new opencv_core.CvRect(cvGetSeqElem(faces,i));
                            int x=r.x(),y=r.y(),w=r.width(),h=r.height();
                            opencv_core.cvRectangle(frame1, cvPoint(x,y),cvPoint(x+w,y+h), opencv_core.CvScalar.GREEN, 2, CV_AA, 0);
                            opencv_core.cvGetSubRect(frame1, face_img, r); 
                          try {
                              new ImageProcessing(face_img);
                          } catch (IOException ex) {
                             
                          } catch (ClassNotFoundException ex) {
                              
                          }
                        }
            
               
                    
                //}
                BufferedImage dis = image.getBufferedImage();
                if(g.drawImage(dis,0,0,dislbl.getWidth(),dislbl.getHeight(),0,0,dis.getWidth(),dis.getHeight(),null)){}

                char c = (char)cvWaitKey(15);
                if(c == 'q')break;
                
            }
            if(runnable == false){
	System.out.println("going to wait");
    try {
        this.wait();
    } catch (InterruptedException ex) {
       
    }}
}
            
        }
} } }



   
class ImageProcessing{
       // opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\myframe.png", face_img);
    public ImageProcessing(){}
    
    public ImageProcessing(CvMat face_img) throws IOException, ClassNotFoundException{
        image = face_img.asIplImage();
        new_img_width = 150;
        new_img_height = 150;
        resizedImage = resizeImg(image,new_img_width,new_img_height);
        this.geometricTransformation(resizedImage);
        
    }
        opencv_core.IplImage image;
        IplImage resizedImage;
        
        //int original_width =
        //int original_height = face_img.rows();
        int new_img_width, new_img_height,counter ;
        IplImage resizeImg(IplImage image,int new_img_width, int new_img_height){
        //IplIamge IplImage new_img = IplImage.create((int)(original_width*0.5),(int)(original_height * 0.5), IPL_DEPTH_8U, 1);
        opencv_core.IplImage new_img =opencv_core.IplImage.create(new_img_width,new_img_height, image.depth(), image.nChannels());
        if(new_img_width <image.width() && new_img_height < image.height()){
            opencv_imgproc.cvResize(image,new_img,opencv_imgproc.CV_INTER_AREA );
            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\dbpix.png", new_img);
            
            return new_img;
        }
        else{
            opencv_imgproc.cvResize(image,new_img,opencv_imgproc.CV_INTER_LINEAR);
            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\dbpix.png", new_img);
            
            return new_img;
        }
}
       // opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\myframe.png", new_img);
        // dest = new_img.asCvMat();
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
        void geometricTransformation(IplImage new_img) throws IOException, ClassNotFoundException{
        opencv_core.IplImage img_gray = opencv_core.IplImage.create(new_img.cvSize(),new_img.depth(),1);
        opencv_imgproc.cvCvtColor(new_img, img_gray,opencv_imgproc.CV_RGB2GRAY );
        opencv_core.Mat mat_gray = opencv_core.Mat.createFrom(img_gray.getBufferedImage());
        opencv_imgproc.equalizeHist(mat_gray,mat_gray);
        opencv_core.IplImage ipl_gray = mat_gray.asIplImage();
        opencv_core.CvMemStorage eyeStorage = opencv_core.CvMemStorage.create();
        opencv_core.cvClearMemStorage(eyeStorage);
        String eyeClassifier = "haarcascade_eye.xml";
        opencv_core.CvRect rightEyeRect =  detectLeftEye(new_img,eyeClassifier,eyeStorage);
          opencv_core.CvRect leftEyeRect =  detectRightEye(new_img,eyeClassifier,eyeStorage);
        // opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\myframe.png",new_img );
         System.out.println("leftEyeRect"+ leftEyeRect.x()+","+leftEyeRect.y()+","+leftEyeRect.width()+","+leftEyeRect.height());
         System.out.println("rightEyeRect"+rightEyeRect.x()+","+rightEyeRect.y()+","+rightEyeRect.width()+","+rightEyeRect.height() );
         opencv_core.CvMat new_mat = new_img.asCvMat();
         if(!(rightEyeRect.y()==0 || leftEyeRect.y()==0||rightEyeRect.x()==0 || leftEyeRect.x()==0||rightEyeRect.x()==leftEyeRect.x())){
          double angle = Math.atan((rightEyeRect.y() - leftEyeRect.y())/((rightEyeRect.x())-leftEyeRect.x()))*180/Math.PI;
          
          if(angle>-45 && angle<45){
          int dw = (int) (cvRound(new_mat.cols()) * 0.5);
            int dh = (int) cvRound(new_mat.rows() * 0.5);
            opencv_core.Point face_center = new opencv_core.Point(dw,dh);
            opencv_core.CvPoint face_cen = face_center.asCvPoint();
            opencv_core.Mat rotate = new opencv_core.Mat();
            opencv_core.Point2f faceCenter = new opencv_core.Point2f(dw,dh);
            rotate = opencv_imgproc.getRotationMatrix2D(faceCenter, angle, 1.0);
            
            //Imgproc.warpAffine(conv,warped , rotation,warped.size() );
            opencv_core.IplImage warp = opencv_core.IplImage.create(new_img.cvSize(),new_img.depth(),1);
             opencv_imgproc.cvCvtColor(new_img, warp,opencv_imgproc.CV_RGB2GRAY );
             opencv_core.Mat warpp = opencv_core.Mat.createFrom(warp.getBufferedImage());
            opencv_core.Mat warped = new opencv_core.Mat();
            opencv_core.Size size = new opencv_core.Size(warpp.size());
            opencv_imgproc.warpAffine(warpp, warped, rotate, size);
            opencv_core.IplImage conve = warped.asIplImage();
            opencv_core.IplImage ed = opencv_core.IplImage.create(warp.cvSize(), warp.depth(),warp.nChannels());
            opencv_core.cvFlip(conve, ed, 1);
             opencv_core.CvRect cropRect1 =  opencv_core.cvRect(20,35,105,105);
        opencv_core.CvMat cropFace1 = new opencv_core.CvMat();
        opencv_core.cvGetSubRect(ed, cropFace1 , cropRect1);
        opencv_core.IplImage new_crop1 = cropFace1.asIplImage();
          
           opencv_core.Mat grayImage=opencv_core.cvarrToMat(new_crop1);
              opencv_core.IplImage grayIpl = grayImage.asIplImage();
              opencv_imgproc.cvEqualizeHist(grayIpl, grayIpl);
              opencv_core.Mat bilateral = new opencv_core.Mat();
       opencv_imgproc.bilateralFilter(grayImage,bilateral,0, 20.0, 2.0, 0);
        opencv_core.CvMat mask= opencv_core.CvMat.create(105,105,opencv_core.CV_8UC1);
       opencv_core.cvSet(mask, opencv_core.CvScalar.GRAY);
      
       opencv_core.Point face_center1 = new opencv_core.Point(cvRound(grayIpl.width()*0.5),cvRound(grayIpl.height()*0.4));
      
       opencv_core.CvPoint face_center2=face_center1.asCvPoint();
       opencv_core.Size sized = new opencv_core.Size(cvRound(grayIpl.width()*0.5),cvRound(grayIpl.height()*0.8));
       opencv_core.CvSize sized1=sized.asCvSize();
     
       opencv_core.cvEllipse(mask, face_center2, sized1, 0, 0, 360,opencv_core.CvScalar.ZERO,opencv_core.CV_FILLED,0,0);
       
       opencv_core.Mat s=opencv_core.cvarrToMat(mask);
     
     opencv_core.Mat fn=opencv_core.addPut(bilateral, s);
     opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\testImage.png",fn.asIplImage());
          Mat testImage = imread("C:\\Users\\User 2\\Desktop\\testImage.png",CV_LOAD_IMAGE_GRAYSCALE);
        int [] predictedLabel = {0};
       double [] confidence = new double [1];
       Mat eigenvectors = facerecog2.getMat("eigenvectors");
        Mat averageFaceRow = facerecog2.getMat("mean");
        Mat projection = opencv_contrib.subspaceProject(eigenvectors, averageFaceRow,testImage.reshape(1,1));
        Mat reconstructionRow = opencv_contrib.subspaceReconstruct(eigenvectors,averageFaceRow, projection);
        Mat reconstructionMat = reconstructionRow.reshape(1,105);
        Mat reconstructedFace = new Mat(reconstructionMat.size(), CV_8U);
        reconstructionMat.convertTo(reconstructedFace, CV_8U, 1, 0);
        double similarity = getSimilarity(testImage,reconstructedFace);
        System.out.println(similarity);
        //double er = Eigen.errorL2;
        //System.out.println(er);
       if(similarity < 0.60){
       
       facerecog2.predict(testImage,predictedLabel,confidence);
       
       System.out.println("PredictedLabel" + Arrays.toString(predictedLabel)+"  "+"Confidence"+ Arrays.toString(confidence));
       
         facerecog2.predict(testImage,predictedLabel,confidence);
       
       System.out.println("PredictedLabel" + Arrays.toString(predictedLabel)+"  "+"Confidence"+ Arrays.toString(confidence));
       
         checkLabelDB(predictedLabel[0]);}
       //mythread.runnable = false;
        //cvReleaseCapture(capture);
        stabtn.setEnabled(true);
        
          
          }
          
          
            }
            
  } 
        
        
        opencv_core.CvRect detectLeftEye(IplImage topLeftOfFace,String eyeClassifier,opencv_core.CvMemStorage storage){
                
            //IplImage con_img = topLeftOfFace.asIplImage();
             opencv_core.CvRect cropRect =  opencv_core.cvRect(75,0,75,90);
        opencv_core.CvMat cropFace = new opencv_core.CvMat();
        opencv_core.cvGetSubRect(topLeftOfFace, cropFace , cropRect);
        IplImage new_crop = cropFace.asIplImage();
            
       IplImage img_gray = IplImage.create(new_crop.width(),new_crop.height(),new_crop.depth(),1);
       opencv_imgproc.cvCvtColor(new_crop, img_gray, opencv_imgproc.CV_RGB2GRAY);
       opencv_imgproc.cvEqualizeHist(img_gray, img_gray);
               opencv_objdetect.CvHaarClassifierCascade eyeDetector = new opencv_objdetect.CvHaarClassifierCascade(opencv_core.cvLoad(eyeClassifier));
             opencv_core.CvSeq eyes=opencv_objdetect.cvHaarDetectObjects(new_crop, eyeDetector, storage, 1.1, 3,opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING,cvSize(0,0),cvSize(45,45));
             opencv_core.CvRect leftEyeRect = new opencv_core.CvRect();
             
             for(int i=0;i<eyes.total();i++){
                  leftEyeRect = new opencv_core.CvRect(cvGetSeqElem(eyes,i));
                    int x=leftEyeRect.x(),y=leftEyeRect.y(),w=leftEyeRect.width(),h=leftEyeRect.height();
                     //opencv_core.cvRectangle(new_crop, cvPoint(x,y),cvPoint(x+w,y+h), CvScalar.BLUE, 2, CV_AA, 0);
              opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\myframe3.png",new_crop );     // System.out.println( x + y);
             }
            return leftEyeRect;
  }
  
  opencv_core.CvRect detectRightEye(IplImage topRightOfFace,String eyeClassifier,opencv_core.CvMemStorage storage){
                
            //IplImage con_img = topRightOfFace.asIplImage();
              opencv_core.CvRect cropRect =  opencv_core.cvRect(0,0,75,90);
        opencv_core.CvMat cropFace = new opencv_core.CvMat();
        opencv_core.cvGetSubRect(topRightOfFace, cropFace , cropRect);
        IplImage new_crop = cropFace.asIplImage();
            IplImage img_gray = IplImage.create(new_crop.width(),new_crop.height(),new_crop.depth(),1);
       opencv_imgproc.cvCvtColor(new_crop, img_gray, opencv_imgproc.CV_RGB2GRAY);
       opencv_imgproc.cvEqualizeHist(img_gray, img_gray);
               opencv_objdetect.CvHaarClassifierCascade eyeDetector = new opencv_objdetect.CvHaarClassifierCascade(opencv_core.cvLoad(eyeClassifier));
             opencv_core.CvSeq eyes=opencv_objdetect.cvHaarDetectObjects(new_crop, eyeDetector, storage, 1.1, 3,opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING,cvSize(0,0),cvSize(45,45));
             opencv_core.CvRect rightEyeRect = new opencv_core.CvRect();
             //opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\myframe5.png",new_crop );
             for(int j=0;j<eyes.total();j++){
                  rightEyeRect = new opencv_core.CvRect(cvGetSeqElem(eyes,j));
                    int x=rightEyeRect.x(),y=rightEyeRect.y(),w=rightEyeRect.width(),h=rightEyeRect.height();
                    // opencv_core.cvRectangle(new_crop, cvPoint(x,y),cvPoint(x+w,y+h), CvScalar.BLUE, 2, CV_AA, 0);
                     opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\myframe4.png",new_crop );
             }
            return rightEyeRect;
  }
  
}


       @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        piclbl = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lntxt = new javax.swing.JTextField();
        adtxt = new javax.swing.JTextField();
        fntxt = new javax.swing.JTextField();
        matxt = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        patxt = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        fatxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        detxt = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        dislbl = new javax.swing.JLabel();
        stobtn = new javax.swing.JButton();
        stabtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Patient Recognition");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(49, 150, 222));
        jLabel6.setText("School Information");
        jLabel6.setToolTipText("");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel7.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(49, 150, 222));
        jLabel7.setText("Face Capture");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(221, 221, 221));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(49, 150, 222))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(221, 221, 221));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator1.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 150, 2));

        jSeparator4.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 395, 150, 2));

        jSeparator6.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 220, 150, 2));

        jSeparator7.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 277, 150, 2));
        jPanel1.add(piclbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 120, 140));

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(49, 150, 222));
        jLabel2.setText("First Name");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 177, -1, -1));

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(49, 150, 222));
        jLabel3.setText("Last Name");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 177, -1, -1));

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(49, 150, 222));
        jLabel4.setText("Matric. No.");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 227, -1, -1));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(49, 150, 222));
        jLabel5.setText("Address");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 345, -1, -1));

        lntxt.setBackground(new java.awt.Color(221, 221, 221));
        lntxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lntxt.setBorder(null);
        lntxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                lntxtFocusLost(evt);
            }
        });
        jPanel1.add(lntxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, 150, 30));

        adtxt.setBackground(new java.awt.Color(221, 221, 221));
        adtxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        adtxt.setBorder(null);
        adtxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                adtxtFocusLost(evt);
            }
        });
        jPanel1.add(adtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 365, 150, 30));

        fntxt.setBackground(new java.awt.Color(221, 221, 221));
        fntxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        fntxt.setBorder(null);
        fntxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fntxtFocusLost(evt);
            }
        });
        jPanel1.add(fntxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 150, 30));

        matxt.setBackground(new java.awt.Color(221, 221, 221));
        matxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        matxt.setBorder(null);
        matxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                matxtFocusLost(evt);
            }
        });
        matxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matxtActionPerformed(evt);
            }
        });
        jPanel1.add(matxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 247, 150, 30));

        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 277, 150, 2));

        patxt.setBackground(new java.awt.Color(221, 221, 221));
        patxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        patxt.setBorder(null);
        jPanel1.add(patxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 247, 150, 30));

        jLabel14.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(49, 150, 222));
        jLabel14.setText("Patient ID");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 227, -1, -1));

        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 337, 150, 2));

        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 337, 150, 2));

        fatxt.setBackground(new java.awt.Color(221, 221, 221));
        fatxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        fatxt.setBorder(null);
        fatxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fatxtFocusLost(evt);
            }
        });
        jPanel1.add(fatxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 307, 150, 30));

        jLabel11.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(49, 150, 222));
        jLabel11.setText("Faculty");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 287, -1, -1));

        jLabel10.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(49, 150, 222));
        jLabel10.setText("Department");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 287, -1, -1));

        detxt.setBackground(new java.awt.Color(221, 221, 221));
        detxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        detxt.setBorder(null);
        detxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                detxtFocusLost(evt);
            }
        });
        jPanel1.add(detxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 307, 150, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 400, 410));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(49, 150, 222))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dislbl.setBackground(new java.awt.Color(221, 221, 221));
        jPanel3.add(dislbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 5, 540, 383));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 560, 400));

        stobtn.setBackground(new java.awt.Color(221, 221, 221));
        stobtn.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        stobtn.setForeground(new java.awt.Color(49, 150, 222));
        stobtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/cam.png"))); // NOI18N
        stobtn.setText("Stop");
        stobtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stobtnActionPerformed(evt);
            }
        });
        getContentPane().add(stobtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 420, -1, -1));

        stabtn.setBackground(new java.awt.Color(221, 221, 221));
        stabtn.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        stabtn.setForeground(new java.awt.Color(49, 150, 222));
        stabtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/cam.png"))); // NOI18N
        stabtn.setText("Start");
        stabtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stabtnActionPerformed(evt);
            }
        });
        getContentPane().add(stabtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 420, -1, -1));

        jLabel1.setBackground(new java.awt.Color(221, 221, 221));
        jLabel1.setForeground(new java.awt.Color(221, 221, 221));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/Regist.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 460));

        jButton3.setBackground(new java.awt.Color(221, 221, 221));
        jButton3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(49, 150, 222));
        jButton3.setText("<html><center><font>Save <br>Data");
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 410, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void stabtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stabtnActionPerformed
        facerecog2.load("C:\\Users\\User 2\\Desktop\\Training set\\faceTraining.xml");
        mythread = new DaemonThread();
        Thread t = new Thread(mythread);
        t.setDaemon(true);
        mythread.runnable = true;
        t.start();
        stabtn.setEnabled(false);
        
        
    }//GEN-LAST:event_stabtnActionPerformed

    private void stobtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stobtnActionPerformed
        mythread.runnable = false;
        cvReleaseCapture(capture);
        stabtn.setEnabled(true);
        
    }//GEN-LAST:event_stobtnActionPerformed

    private void fntxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fntxtFocusLost
        
    }//GEN-LAST:event_fntxtFocusLost

    private void lntxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lntxtFocusLost
        
    }//GEN-LAST:event_lntxtFocusLost

    private void matxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_matxtActionPerformed

    private void matxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_matxtFocusLost
        
    }//GEN-LAST:event_matxtFocusLost

    private void adtxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_adtxtFocusLost
            
    }//GEN-LAST:event_adtxtFocusLost

    private void dobFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dobFocusLost
            
    }//GEN-LAST:event_dobFocusLost

    private void fatxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fatxtFocusLost
        
    }//GEN-LAST:event_fatxtFocusLost

    private void detxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_detxtFocusLost
        
    }//GEN-LAST:event_detxtFocusLost

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        new HomePage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Recognition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Recognition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Recognition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Recognition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Recognition().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Recognition.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField adtxt;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField detxt;
    private javax.swing.JLabel dislbl;
    private javax.swing.JTextField fatxt;
    private javax.swing.JTextField fntxt;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTextField lntxt;
    private javax.swing.JTextField matxt;
    private javax.swing.JTextField patxt;
    private javax.swing.JLabel piclbl;
    private javax.swing.JButton stabtn;
    private javax.swing.JButton stobtn;
    // End of variables declaration//GEN-END:variables
}
