/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;

import java.awt.Color;
import java.awt.Graphics;
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
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import org.bytedeco.javacpp.opencv_contrib;
import static org.bytedeco.javacpp.opencv_contrib.createEigenFaceRecognizer;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
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
public class Registration1 extends javax.swing.JFrame {

    /**
     * Creates new form Registration1^
     */
    public Registration1() throws IOException {
        initComponents();
        mard.setSelected(true);
        gender = "Male";
        inWriteCounts();
        rndID();
        patxt.setEditable(false);
        //labels = 0;
        num = 0;
        //stabtn.setEnabled(false);
    }
    private DaemonThread mythread = new DaemonThread();
    CvCapture capture;
    String gender, passport;
    ValidateFields valid = new ValidateFields();
    String[] artcos = {"English and Litrary Studies", "Geography", "History and International Studies, Economics", "Political Science"};
    String[] scicos = {"Biological Sciences", "Chemstry", "Computer Sciences", "Geology", "Mathematics", "Statistics", "Physics"};

    int labels, num;

    public void rndID() {
        String contains = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(contains.charAt(rnd.nextInt(contains.length())));
        }
        patxt.setText(sb.toString());
        //
    }

    public void inWriteCounts() throws IOException {
        BufferedWriter val = new BufferedWriter(new FileWriter("cal.txt"));
//            //BufferedWriter checkAlarm = new BufferedWriter(new FileWriter("//Checks//alarm.txt"));
        val.write(String.valueOf(0));
        val.close();

    }

    public void disposeRegForm() {
        this.dispose();
    }

    class DaemonThread implements Runnable {

        protected volatile boolean runnable = false;

        @Override
        @SuppressWarnings({"empty-statement", "UnusedAssignment", "ResultOfObjectAllocationIgnored"})
        public void run() {
            synchronized (this) {
                while (runnable) {

                    System.out.println("Working!!!");
                    capture = cvCreateCameraCapture(0);
                    opencv_highgui.cvSetCaptureProperty(capture, CV_CAP_PROP_FRAME_WIDTH, 640);
                    opencv_highgui.cvSetCaptureProperty(capture, CV_CAP_PROP_FRAME_HEIGHT, 480);

                    opencv_core.Mat frame = new opencv_core.Mat();
                    Graphics g;
                    g = dislbl.getGraphics();
                    String fname = "haarcascade_frontalface_alt2.xml";

                    opencv_objdetect.CvHaarClassifierCascade fnames = new opencv_objdetect.CvHaarClassifierCascade(opencv_core.cvLoad(fname));
                    opencv_core.IplImage image;
                    opencv_core.CvMat face_img = new opencv_core.CvMat();
                    while (true) {
                        opencv_core.CvMemStorage faceStorage = opencv_core.CvMemStorage.create();
                        //image = cvQueryFrame(capture1);

                        if (!capture.isNull()) {
                            image = cvQueryFrame(capture);
                            //image = frame.asIplImage();
                            opencv_core.cvFlip(image, image, 1);
                            opencv_core.IplImage frame1 = image;
                            opencv_core.cvClearMemStorage(faceStorage);
                            //IplImage image1=IplImage.create(frame1.width(),frame1.height(), IPL_DEPTH_8U, 1);
                            opencv_core.IplImage frame_gray = opencv_core.IplImage.create(frame1.width(), frame1.height(), IPL_DEPTH_8U, 1);
                            opencv_imgproc.cvCvtColor(frame1, frame_gray, opencv_imgproc.CV_BGR2GRAY);
                            opencv_imgproc.cvEqualizeHist(frame_gray, frame_gray);

                            opencv_core.CvSeq faces = opencv_objdetect.cvHaarDetectObjects(frame_gray, fnames, faceStorage, 1.1, 3, opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING, cvSize(80, 80), cvSize(250, 250));
                            System.out.println(faces.total());

                            for (int i = 0; i < faces.total(); i++) {
                                opencv_core.CvRect r = new opencv_core.CvRect(cvGetSeqElem(faces, i));
                                int x = r.x(), y = r.y(), w = r.width(), h = r.height();
                                opencv_core.cvRectangle(frame1, cvPoint(x, y), cvPoint(x + w, y + h), opencv_core.CvScalar.GREEN, 2, CV_AA, 0);
                                opencv_core.cvGetSubRect(frame1, face_img, r);
                                try {
                                    new ImageProcessing(face_img);
                                } catch (IOException ex) {
                                    Logger.getLogger(Registration1.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (ClassNotFoundException ex) {
                                    Logger.getLogger(Registration1.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                            //}
                            BufferedImage dis = image.getBufferedImage();
                            if (g.drawImage(dis, 0, 0, dislbl.getWidth(), dislbl.getHeight(), 0, 0, dis.getWidth(), dis.getHeight(), null)) {
                            }

                            char c = (char) cvWaitKey(15);
                            if (c == 'q') {
                                break;
                            }

                        }
                        if (runnable == false) {
                            System.out.println("going to wait");
                            try {
                                this.wait();
                            } catch (InterruptedException ex) {

                            }
                        }
                    }

                }
            }
        }
    }

    public void saveData() throws ClassNotFoundException {
        System.out.println("DBDBDBDBDDBDBDBDBDBDBBDDBDBBDBDBDBDDB");
        try {
            InputStream fis = new FileInputStream(new File(passport));
            PreparedStatement ps, ps1;
            Connection conn = DBConn.dataBase();
            String sql = "INSERT INTO STUDENTDATA (MATRICNUM,LABEL,FIRSTNAME,LASTNAME,GENDER,FACULTY,DEPARTMENT,DOB,ADDRESS,PASSPORT) VALUES (?,?,?,?,?,?,?,?,?,?)";
            String sql1 = "INSERT INTO PATIENTSDATA (PATIENTID,LABEL,WEIGHT,HEIGHT,BLOODGROUP,GENOTYPE) VALUES (?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps1 = conn.prepareStatement(sql1);

            ps.setString(1, matxt.getText());
            ps.setInt(2, labels);
            ps.setString(3, fntxt.getText());
            ps.setString(4, lntxt.getText());
            ps.setString(5, gender);
            ps.setString(6, facom.getSelectedItem().toString());
            ps.setString(7, decom.getSelectedItem().toString());
            ps.setString(8, ((JTextField) dob.getDateEditor().getUiComponent()).getText());
            ps.setString(9, adtxt.getText());
            ps.setBlob(10, fis);

            ps1.setString(1, patxt.getText());
            ps1.setInt(2, labels);
            ps1.setInt(3, Integer.parseInt(wetxt.getText()));
            ps1.setInt(4, Integer.parseInt(hitxt.getText()));
            ps1.setString(5, blcom.getSelectedItem().toString());
            ps1.setString(6, gecom.getSelectedItem().toString());

            int InsertRec = ps.executeUpdate();
            int InsertRec1 = ps1.executeUpdate();
            if (InsertRec > 0 && InsertRec1 > 0) {
                System.out.println("DBDBDBDBDDBDBDBDBDBDBBDDBDBBDBDBDBDDB");
                JOptionPane.showMessageDialog(null, "Inserted into the Database");
            } else {
                JOptionPane.showMessageDialog(null, "Inserted not into  the Database");
            }

        } catch (SQLException f) {
            f.getMessage();
        } catch (FileNotFoundException e) {
            e.getMessage();
        }

    }

    class SaveProcessedImg {

        public SaveProcessedImg(IplImage image, int count) throws IOException, ClassNotFoundException {
            // int counter = count;
            this.saveImg(image, count);
            //System.out.println(count);
        }
        // int labels;

        public int readLabels() throws IOException {
            String val = null;
            try {
                BufferedReader checkVal = new BufferedReader(new FileReader("Labs.txt"));
                val = checkVal.readLine();
                //System.out.println(val);
                checkVal.close();
            } catch (FileNotFoundException ex) {
            }
            return Integer.parseInt(val);
        }

        public void writeLabels(int s) throws IOException {
            BufferedWriter val = new BufferedWriter(new FileWriter("Labs.txt"));
//            //BufferedWriter checkAlarm = new BufferedWriter(new FileWriter("//Checks//alarm.txt"));
            val.write(String.valueOf(s));
            val.close();

        }

        public void saveImg(opencv_core.IplImage image, int counter) throws IOException, ClassNotFoundException {

            String datum = fntxt.getText();
            if ((datum.matches("[A-Z][a-zA-Z]+"))) {
                if (counter < 20) {
                    switch (counter) {

                        case 0:
                            int lab = compareLabel();
                            // = lab;
                            writeLabels(lab);
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + lab + "-" + datum + "_" + counter + ".png", image);
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\Recognition\\" + lab + "-" + datum + "_" + counter + ".png", image);
                            //counter++;
                            //labels = lab;

                            break;
                        case 1:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);
                            //counter++;
                            labels = readLabels();
                            System.out.println(labels);
                            break;
                        case 2:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);
                            //counter++;
                            break;
                        case 3:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);
                            //counter++;
                            break;
                        case 4:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);

                            //counter++;
                            break;
                        case 5:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);
                            //recognition();
                            counter++;
                            break;
                        case 6:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);
                            //recognition();
                            //counter++;
                            break;
                        case 7:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);
                            //recognition();
                            //counter++;
                            break;
                        case 8:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);
                            //recognition();
                            //counter++;
                            break;
                        case 9:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);
                            //recognition();
                            //counter++;
                            break;
                        case 10:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);
                            //recognition();
                            //counter++;
                            break;
                        case 11:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);
                            //recognition();
                            //counter++;
                            break;
                        case 12:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);
                            //recognition();
                            //counter++;
                            break;
                        case 13:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);
                            //recognition();
                            //counter++;
                            break;
                        case 14:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);
                            //recognition();
                            //counter++;
                            break;
                        case 15:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);
                            //recognition();
                            //counter++;
                            break;
                        case 16:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);
                            //recognition();
                            //counter++;
                            break;
                        case 17:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);
                            //recognition();
                            //counter++;
                            break;
                        case 18:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);
                            //recognition();
                            //counter++;
                            break;
                        case 19:
                            opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\" + readLabels() + "-" + datum + "_" + counter + ".png", image);
                            new Training().trainSet();
                            //counter++;
                            break;

                    }
                } else {
                    System.out.println("Enough Training set");
                    patxt.setText("");

                }
            } else {
                System.out.println("Name starts with uppercase or Empty field detected\n Try again!!!");
                patxt.setText("");
            }

        }

        public int labe(int data) {
            return data;

        }

        public void compareFileName(String data) {
            String trainingDir = "C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\";
            String mytext;
            File root = new File(trainingDir);
            FilenameFilter imgfilter = (File dir, String name) -> {
                name = name.toLowerCase();
                return name.endsWith(".png");
            };
            File[] imageFile = root.listFiles(imgfilter);

            String compare;
            for (int i = 0; i < imageFile.length; i++) {
                char[] cons = imageFile[i].toString().toCharArray();

                compare = imageFile[i].toString().substring(37, cons.length - 5);
                System.out.println(compare);
                // String compare1 = compare.substring(31);
                if (data.equalsIgnoreCase(compare)) {
                    System.out.println("Name already exist. Please use another name to continue.");
                    data = "";
                    mytext = data;
                    //break;   
                } else {
                    mytext = data;
                }
            }

        }

        public int compareLabel() {
            String trainingDir = "C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\";
            File root = new File(trainingDir);
            int label = 0;
            if (root.list().length == 0) {

            } else {
                FilenameFilter imgfilter = (File dir, String name) -> {
                    name = name.toLowerCase();
                    return name.endsWith(".png");
                };
                File[] imageFile = root.listFiles(imgfilter);
                int lab = 0;
                //String compare;
                for (File imageFile1 : imageFile) {
                    //char [] cons = imageFile[i].toString().toCharArray();
                    lab = Integer.parseInt(imageFile1.getName().split("\\-")[0]);
                    //System.out.println(compare);
                }
                label = lab;
                label++;

            }
            return label;
        }

    }

    class Training {

        public void trainSet() throws ClassNotFoundException {
            saveData();

            String training_dir = "C:\\Users\\User 2\\Desktop\\Training set\\TrainingSet\\";
            File root = new File(training_dir);
            // FileNameExtensionFilter imgfilter = new FileNameExtensionFilter("image formate","JPG");
            FilenameFilter imgfilter = (File dir, String name) -> {
                name = name.toLowerCase();
                return name.endsWith(".png");
            };
            File[] imageFile = root.listFiles(imgfilter);

            opencv_core.MatVector images = new opencv_core.MatVector(imageFile.length);

            opencv_core.Mat labelss = new opencv_core.Mat(imageFile.length, 1, CV_32SC1);

            IntBuffer labelbuff = labelss.getIntBuffer();
            //System.out.println(labels);
            int countre = 0;

            for (File image : imageFile) {
                opencv_core.Mat img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);
                int label = Integer.parseInt(image.getName().split("\\-")[0]);
                System.out.println(label);
                images.put(countre, img);
                System.out.println(img);
                labelbuff.put(countre, label);
                countre++;

            }
            //FaceRecognizer facerecog = createFisherFaceRecognizer();
            opencv_contrib.FaceRecognizer facerecog2 = createEigenFaceRecognizer();
            //FaceRecognizer facerecog2 = createLBPHFaceRecognizer();
            System.out.println(images);
            facerecog2.train(images, labelss);
            // int predictedLabel = facerecog2.predict(testImage);
            facerecog2.save("C:\\Users\\User 2\\Desktop\\Training set\\faceTraining.xml");

            opencv_core.Mat eigenvectors = facerecog2.getMat("eigenvectors");
            for (int i = 0; i < min(20, eigenvectors.cols()); i++) {
                // Create a continuous column vector from eigenvector #i.
                opencv_core.Mat eigenvector = eigenvectors.col(i).clone();
                opencv_core.Mat eigenface = getImageFrom1DFloatMat(eigenvector, 105);
                opencv_core.IplImage eigenfaces = eigenface.asIplImage();
                //String txt = getxtlbl.getText();
                opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\Training set\\Eigenvector\\" + "Eigenface" + "_" + i + ".png", eigenfaces);

            }
        }

        public opencv_core.Mat getImageFrom1DFloatMat(opencv_core.Mat matrixRow, int height1) {
// Make a rectangular shaped image instead of a single row.
            opencv_core.Mat rectangularMat = matrixRow.reshape(1, height1);
// Scale the values to be between 0 to 255 and store them
// as a regular 8-bit uchar image.
            opencv_core.Mat dst = new opencv_core.Mat();
            opencv_core.normalize(rectangularMat, dst, 0, 255, NORM_MINMAX, CV_8UC1, dst);//normalize(rectangularMat, dst, 0, 255, NORM_MINMAX,CV_8UC1);
            return dst;
        }
    }

    class ImageProcessing {
        // opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\myframe.png", face_img);

        public ImageProcessing() {
        }

        public ImageProcessing(CvMat face_img) throws IOException, ClassNotFoundException {
            image = face_img.asIplImage();
            new_img_width = 150;
            new_img_height = 150;
            resizedImage = resizeImg(image, new_img_width, new_img_height);
            //counter = 0;
            //writeCounter(counter);
            counter = readCounts();
            this.geometricTransformation(resizedImage);

        }
        opencv_core.IplImage image;
        IplImage resizedImage;

        //int original_width =
        //int original_height = face_img.rows();
        int new_img_width, new_img_height, counter;

        IplImage resizeImg(IplImage image, int new_img_width, int new_img_height) {
            //IplIamge IplImage new_img = IplImage.create((int)(original_width*0.5),(int)(original_height * 0.5), IPL_DEPTH_8U, 1);
            opencv_core.IplImage new_img = opencv_core.IplImage.create(new_img_width, new_img_height, image.depth(), image.nChannels());
            if (new_img_width < image.width() && new_img_height < image.height()) {
                opencv_imgproc.cvResize(image, new_img, opencv_imgproc.CV_INTER_AREA);
                opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\dbpix.png", new_img);
                passport = "C:\\Users\\User 2\\Desktop\\dbpix.png";

                return new_img;
            } else {
                opencv_imgproc.cvResize(image, new_img, opencv_imgproc.CV_INTER_LINEAR);
                opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\dbpix.png", new_img);
                passport = "C:\\Users\\User 2\\Desktop\\dbpix.png";

                return new_img;
            }
        }
        // opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\myframe.png", new_img);
        // dest = new_img.asCvMat();

        @SuppressWarnings("ResultOfObjectAllocationIgnored")
        void geometricTransformation(IplImage new_img) throws IOException, ClassNotFoundException {
            opencv_core.IplImage img_gray = opencv_core.IplImage.create(new_img.cvSize(), new_img.depth(), 1);
            opencv_imgproc.cvCvtColor(new_img, img_gray, opencv_imgproc.CV_RGB2GRAY);
            opencv_core.Mat mat_gray = opencv_core.Mat.createFrom(img_gray.getBufferedImage());
            opencv_imgproc.equalizeHist(mat_gray, mat_gray);
            opencv_core.IplImage ipl_gray = mat_gray.asIplImage();
            opencv_core.CvMemStorage eyeStorage = opencv_core.CvMemStorage.create();
            opencv_core.cvClearMemStorage(eyeStorage);
            String eyeClassifier = "haarcascade_eye.xml";
            opencv_core.CvRect rightEyeRect = detectLeftEye(new_img, eyeClassifier, eyeStorage);
            opencv_core.CvRect leftEyeRect = detectRightEye(new_img, eyeClassifier, eyeStorage);
            // opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\myframe.png",new_img );
            System.out.println("leftEyeRect" + leftEyeRect.x() + "," + leftEyeRect.y() + "," + leftEyeRect.width() + "," + leftEyeRect.height());
            System.out.println("rightEyeRect" + rightEyeRect.x() + "," + rightEyeRect.y() + "," + rightEyeRect.width() + "," + rightEyeRect.height());
            opencv_core.CvMat new_mat = new_img.asCvMat();
            if (!(rightEyeRect.y() == 0 || leftEyeRect.y() == 0 || rightEyeRect.x() == 0 || leftEyeRect.x() == 0 || rightEyeRect.x() == leftEyeRect.x())) {
                double angle = Math.atan((rightEyeRect.y() - leftEyeRect.y()) / ((rightEyeRect.x()) - leftEyeRect.x())) * 180 / Math.PI;

                if (angle > -45 && angle < 45) {
                    int dw = (int) (cvRound(new_mat.cols()) * 0.5);
                    int dh = (int) cvRound(new_mat.rows() * 0.5);
                    opencv_core.Point face_center = new opencv_core.Point(dw, dh);
                    opencv_core.CvPoint face_cen = face_center.asCvPoint();
                    opencv_core.Mat rotate = new opencv_core.Mat();
                    opencv_core.Point2f faceCenter = new opencv_core.Point2f(dw, dh);
                    rotate = opencv_imgproc.getRotationMatrix2D(faceCenter, angle, 1.0);

                    //Imgproc.warpAffine(conv,warped , rotation,warped.size() );
                    opencv_core.IplImage warp = opencv_core.IplImage.create(new_img.cvSize(), new_img.depth(), 1);
                    opencv_imgproc.cvCvtColor(new_img, warp, opencv_imgproc.CV_RGB2GRAY);
                    opencv_core.Mat warpp = opencv_core.Mat.createFrom(warp.getBufferedImage());
                    opencv_core.Mat warped = new opencv_core.Mat();
                    opencv_core.Size size = new opencv_core.Size(warpp.size());
                    opencv_imgproc.warpAffine(warpp, warped, rotate, size);
                    opencv_core.IplImage conve = warped.asIplImage();
                    opencv_core.IplImage ed = opencv_core.IplImage.create(warp.cvSize(), warp.depth(), warp.nChannels());
                    opencv_core.cvFlip(conve, ed, 1);
                    opencv_core.CvRect cropRect1 = opencv_core.cvRect(20, 35, 105, 105);
                    opencv_core.CvMat cropFace1 = new opencv_core.CvMat();
                    opencv_core.cvGetSubRect(ed, cropFace1, cropRect1);
                    opencv_core.IplImage new_crop1 = cropFace1.asIplImage();

                    opencv_core.Mat grayImage = opencv_core.cvarrToMat(new_crop1);
                    opencv_core.IplImage grayIpl = grayImage.asIplImage();
                    opencv_imgproc.cvEqualizeHist(grayIpl, grayIpl);
                    opencv_core.Mat bilateral = new opencv_core.Mat();
                    opencv_imgproc.bilateralFilter(grayImage, bilateral, 0, 20.0, 2.0, 0);
                    opencv_core.CvMat mask = opencv_core.CvMat.create(105, 105, opencv_core.CV_8UC1);
                    opencv_core.cvSet(mask, opencv_core.CvScalar.GRAY);

                    opencv_core.Point face_center1 = new opencv_core.Point(cvRound(grayIpl.width() * 0.5), cvRound(grayIpl.height() * 0.4));

                    opencv_core.CvPoint face_center2 = face_center1.asCvPoint();
                    opencv_core.Size sized = new opencv_core.Size(cvRound(grayIpl.width() * 0.5), cvRound(grayIpl.height() * 0.8));
                    opencv_core.CvSize sized1 = sized.asCvSize();

                    opencv_core.cvEllipse(mask, face_center2, sized1, 0, 0, 360, opencv_core.CvScalar.ZERO, opencv_core.CV_FILLED, 0, 0);

                    opencv_core.Mat s = opencv_core.cvarrToMat(mask);

                    opencv_core.Mat fn = opencv_core.addPut(bilateral, s);
                    int i = 0;
                    if (!(fntxt.getText().isEmpty() || lntxt.getText().isEmpty() || matxt.getText().isEmpty() || adtxt.getText().isEmpty() || wetxt.getText().isEmpty() || hitxt.getText().isEmpty())) {
                        if (counter <= 19) {
                            //counter = readCounter();
                            new SaveProcessedImg(fn.asIplImage(), counter);
                            //readCounter();
                            i = counter + 1;
                            writeCounts(i);
                            System.out.println(i);

                        } else {
                            mythread.runnable = false;
                            cvReleaseCapture(capture);
                            int response = JOptionPane.showConfirmDialog(null, "<html><center>Do you want to register another patient?</center>", "Confirm",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (response == JOptionPane.NO_OPTION) {
                                disposeRegForm();
                            } else if (response == JOptionPane.YES_OPTION) {

                                stabtn.setEnabled(true);
                                num = 1;
                                fntxt.setText("");
                                lntxt.setText("");
                                matxt.setText("");
                                adtxt.setText("");
                                patxt.setText("");
                                wetxt.setText("");
                                hitxt.setText("");
                                mard.setSelected(true);
                                facom.setSelectedIndex(0);
                                decom.setSelectedIndex(0);
                                blcom.setSelectedIndex(0);
                                gecom.setSelectedIndex(0);
                                rndID();
                            }

                        }
                    }
                    // else{JOptionPane.showMessageDialog(null, "Empty field detected!!!");}
                } else {
                    System.out.println("Please position your head properly. Thanks for your cooperation.");
                }

            } else {
                System.out.println("Eyes not detected try again!!!");
            }

        }

        public int readCounts() throws IOException {
            String val = null;
            try {
                BufferedReader readval = new BufferedReader(new FileReader("cal.txt"));
                val = readval.readLine();
                //System.out.println(val);
                readval.close();
            } catch (FileNotFoundException ex) {
            }
            return Integer.parseInt(val);
        }

        public void writeCounts(int s) throws IOException {
            BufferedWriter writeval = new BufferedWriter(new FileWriter("cal.txt"));
            writeval.write(String.valueOf(s));
            writeval.close();

        }

        opencv_core.CvRect detectLeftEye(IplImage topLeftOfFace, String eyeClassifier, opencv_core.CvMemStorage storage) {

            //IplImage con_img = topLeftOfFace.asIplImage();
            opencv_core.CvRect cropRect = opencv_core.cvRect(75, 0, 75, 90);
            opencv_core.CvMat cropFace = new opencv_core.CvMat();
            opencv_core.cvGetSubRect(topLeftOfFace, cropFace, cropRect);
            IplImage new_crop = cropFace.asIplImage();

            IplImage img_gray = IplImage.create(new_crop.width(), new_crop.height(), new_crop.depth(), 1);
            opencv_imgproc.cvCvtColor(new_crop, img_gray, opencv_imgproc.CV_RGB2GRAY);
            opencv_imgproc.cvEqualizeHist(img_gray, img_gray);
            opencv_objdetect.CvHaarClassifierCascade eyeDetector = new opencv_objdetect.CvHaarClassifierCascade(opencv_core.cvLoad(eyeClassifier));
            opencv_core.CvSeq eyes = opencv_objdetect.cvHaarDetectObjects(new_crop, eyeDetector, storage, 1.1, 3, opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING, cvSize(0, 0), cvSize(45, 45));
            opencv_core.CvRect leftEyeRect = new opencv_core.CvRect();

            for (int i = 0; i < eyes.total(); i++) {
                leftEyeRect = new opencv_core.CvRect(cvGetSeqElem(eyes, i));
                int x = leftEyeRect.x(), y = leftEyeRect.y(), w = leftEyeRect.width(), h = leftEyeRect.height();
                //opencv_core.cvRectangle(new_crop, cvPoint(x,y),cvPoint(x+w,y+h), CvScalar.BLUE, 2, CV_AA, 0);
                opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\myframe3.png", new_crop);     // System.out.println( x + y);
            }
            return leftEyeRect;
        }

        opencv_core.CvRect detectRightEye(IplImage topRightOfFace, String eyeClassifier, opencv_core.CvMemStorage storage) {

            //IplImage con_img = topRightOfFace.asIplImage();
            opencv_core.CvRect cropRect = opencv_core.cvRect(0, 0, 75, 90);
            opencv_core.CvMat cropFace = new opencv_core.CvMat();
            opencv_core.cvGetSubRect(topRightOfFace, cropFace, cropRect);
            IplImage new_crop = cropFace.asIplImage();
            IplImage img_gray = IplImage.create(new_crop.width(), new_crop.height(), new_crop.depth(), 1);
            opencv_imgproc.cvCvtColor(new_crop, img_gray, opencv_imgproc.CV_RGB2GRAY);
            opencv_imgproc.cvEqualizeHist(img_gray, img_gray);
            opencv_objdetect.CvHaarClassifierCascade eyeDetector = new opencv_objdetect.CvHaarClassifierCascade(opencv_core.cvLoad(eyeClassifier));
            opencv_core.CvSeq eyes = opencv_objdetect.cvHaarDetectObjects(new_crop, eyeDetector, storage, 1.1, 3, opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING, cvSize(0, 0), cvSize(45, 45));
            opencv_core.CvRect rightEyeRect = new opencv_core.CvRect();
            //opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\myframe5.png",new_crop );
            for (int j = 0; j < eyes.total(); j++) {
                rightEyeRect = new opencv_core.CvRect(cvGetSeqElem(eyes, j));
                int x = rightEyeRect.x(), y = rightEyeRect.y(), w = rightEyeRect.width(), h = rightEyeRect.height();
                // opencv_core.cvRectangle(new_crop, cvPoint(x,y),cvPoint(x+w,y+h), CvScalar.BLUE, 2, CV_AA, 0);
                opencv_highgui.cvSaveImage("C:\\Users\\User 2\\Desktop\\myframe4.png", new_crop);
            }
            return rightEyeRect;
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        decom = new javax.swing.JComboBox<>();
        facom = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lntxt = new javax.swing.JTextField();
        adtxt = new javax.swing.JTextField();
        fntxt = new javax.swing.JTextField();
        matxt = new javax.swing.JTextField();
        dob = new com.toedter.calendar.JDateChooser();
        ferd = new javax.swing.JRadioButton();
        mard = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        hitxt = new javax.swing.JTextField();
        wetxt = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        gecom = new javax.swing.JComboBox<>();
        blcom = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        patxt = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        dislbl = new javax.swing.JLabel();
        stobtn = new javax.swing.JButton();
        stabtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Patient Registration");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(49, 150, 222));
        jLabel15.setText("Face Capture");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, -1, -1));

        jLabel16.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(49, 150, 222));
        jLabel16.setText("School Information");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel17.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(49, 150, 222));
        jLabel17.setText("Medical Information");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        jPanel1.setBackground(new java.awt.Color(221, 221, 221));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(49, 150, 222))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(221, 221, 221));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator1.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 67, 150, 2));

        jSeparator4.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 238, 150, 2));

        jSeparator6.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 67, 150, 2));

        jSeparator7.setForeground(new java.awt.Color(49, 150, 222));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 119, 150, 2));

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(49, 150, 222));
        jLabel2.setText("First Name");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(49, 150, 222));
        jLabel3.setText("Last Name");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, -1, -1));

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(49, 150, 222));
        jLabel4.setText("Matric. No.");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 75, -1, -1));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(49, 150, 222));
        jLabel5.setText("Address");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(49, 150, 222));
        jLabel6.setText("Date of Birth");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, -1, -1));

        jLabel7.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(49, 150, 222));
        jLabel7.setText("Department");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        decom.setBackground(new java.awt.Color(49, 150, 222));
        decom.setForeground(new java.awt.Color(49, 150, 222));
        decom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "English and Litrary Studies", "Geography", "History and International Studies", "Economics", "Political Science" }));
        decom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decomActionPerformed(evt);
            }
        });
        jPanel1.add(decom, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 160, -1));

        facom.setBackground(new java.awt.Color(49, 150, 222));
        facom.setForeground(new java.awt.Color(49, 150, 222));
        facom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Art and Social Sciences", "Sciences" }));
        facom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facomActionPerformed(evt);
            }
        });
        jPanel1.add(facom, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 95, 160, -1));

        jLabel8.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(49, 150, 222));
        jLabel8.setText("Faculty");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 75, -1, -1));

        jLabel9.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(49, 150, 222));
        jLabel9.setText("Gender");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, -1, -1));

        lntxt.setBackground(new java.awt.Color(221, 221, 221));
        lntxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lntxt.setBorder(null);
        lntxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                lntxtFocusLost(evt);
            }
        });
        jPanel1.add(lntxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 38, 150, 30));

        adtxt.setBackground(new java.awt.Color(221, 221, 221));
        adtxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        adtxt.setBorder(null);
        adtxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                adtxtFocusLost(evt);
            }
        });
        jPanel1.add(adtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 150, 30));

        fntxt.setBackground(new java.awt.Color(221, 221, 221));
        fntxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        fntxt.setBorder(null);
        fntxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fntxtFocusLost(evt);
            }
        });
        jPanel1.add(fntxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 38, 150, 30));

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
        jPanel1.add(matxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 150, 30));

        dob.setBackground(new java.awt.Color(221, 221, 221));
        dob.setDateFormatString("yyyy-MM-dd");
        dob.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dobFocusLost(evt);
            }
        });
        jPanel1.add(dob, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 150, -1));

        buttonGroup1.add(ferd);
        ferd.setForeground(new java.awt.Color(49, 150, 222));
        ferd.setText("Female");
        ferd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ferdActionPerformed(evt);
            }
        });
        jPanel1.add(ferd, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, -1, -1));

        buttonGroup1.add(mard);
        mard.setForeground(new java.awt.Color(49, 150, 222));
        mard.setText("Male");
        mard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mardActionPerformed(evt);
            }
        });
        jPanel1.add(mard, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 150, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 400, 260));

        jPanel2.setBackground(new java.awt.Color(221, 221, 221));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(49, 150, 222))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(221, 221, 221));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 150, 2));

        jLabel10.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(49, 150, 222));
        jLabel10.setText("Height (cm)");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 133, -1, -1));

        jLabel11.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(49, 150, 222));
        jLabel11.setText("Weight (kg)");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 74, -1, -1));

        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 150, 2));

        hitxt.setBackground(new java.awt.Color(221, 221, 221));
        hitxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        hitxt.setBorder(null);
        hitxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                hitxtFocusLost(evt);
            }
        });
        jPanel2.add(hitxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 150, 30));

        wetxt.setBackground(new java.awt.Color(221, 221, 221));
        wetxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        wetxt.setBorder(null);
        wetxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                wetxtFocusLost(evt);
            }
        });
        jPanel2.add(wetxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 150, 30));

        jLabel12.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(49, 150, 222));
        jLabel12.setText("Blood Group");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, -1, -1));

        jLabel13.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(49, 150, 222));
        jLabel13.setText("Genotype");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, -1, -1));

        gecom.setBackground(new java.awt.Color(49, 150, 222));
        gecom.setForeground(new java.awt.Color(49, 150, 222));
        gecom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AA", "AS", "AC", "SS", "SC", "CC" }));
        gecom.setBorder(null);
        jPanel2.add(gecom, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 130, -1));

        blcom.setBackground(new java.awt.Color(49, 150, 222));
        blcom.setForeground(new java.awt.Color(49, 150, 222));
        blcom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "AB", "B", "O" }));
        blcom.setBorder(null);
        jPanel2.add(blcom, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 130, -1));

        jLabel14.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(49, 150, 222));
        jLabel14.setText("Patient ID");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(49, 150, 222)));
        jPanel2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 65, 150, 2));

        patxt.setBackground(new java.awt.Color(221, 221, 221));
        patxt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        patxt.setBorder(null);
        jPanel2.add(patxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 35, 150, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 400, 190));

        jPanel3.setBackground(new java.awt.Color(221, 221, 221));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(49, 150, 222))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(221, 221, 221));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dislbl.setBackground(new java.awt.Color(221, 221, 221));
        jPanel3.add(dislbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 540, 383));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 560, 410));

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
        getContentPane().add(stobtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 440, -1, -1));

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
        getContentPane().add(stabtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 440, -1, -1));

        jLabel1.setBackground(new java.awt.Color(221, 221, 221));
        jLabel1.setForeground(new java.awt.Color(221, 221, 221));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/Regist.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 500));

        jButton3.setBackground(new java.awt.Color(221, 221, 221));
        jButton3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(49, 150, 222));
        jButton3.setText("<html><center><font>Save <br>Data");
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 410, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void decomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_decomActionPerformed

    private void stabtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stabtnActionPerformed
        if (num == 0) {
            mythread = new DaemonThread();
            Thread t = new Thread(mythread);
            t.setDaemon(true);
            mythread.runnable = true;
            t.start();
            stabtn.setEnabled(false);
        } else {
            mythread = new DaemonThread();
            Thread t = new Thread(mythread);
            t.setDaemon(true);
            mythread.runnable = true;
            t.start();
            stabtn.setEnabled(false);
            num = 0;
            try {
                inWriteCounts();
            } catch (IOException ex) {

            }
        }
    }//GEN-LAST:event_stabtnActionPerformed

    private void stobtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stobtnActionPerformed
        mythread.runnable = false;
        cvReleaseCapture(capture);
        stabtn.setEnabled(true);

    }//GEN-LAST:event_stobtnActionPerformed

    private void mardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mardActionPerformed
        gender = "Male";
    }//GEN-LAST:event_mardActionPerformed

    private void ferdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ferdActionPerformed
        gender = "Female";
    }//GEN-LAST:event_ferdActionPerformed

    private void fntxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fntxtFocusLost
        boolean fn = !valid.validateFirstName(fntxt.getText());
        if (fn) {
            fntxt.setBorder(BorderFactory.createLineBorder(Color.red));
        } else {
            fntxt.setBorder(null);
        }

    }//GEN-LAST:event_fntxtFocusLost

    private void lntxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lntxtFocusLost
        boolean ln = !valid.validateFirstName(lntxt.getText());
        if (ln) {
            lntxt.setBorder(BorderFactory.createLineBorder(Color.red));
        } else {
            lntxt.setBorder(null);
        }

    }//GEN-LAST:event_lntxtFocusLost

    private void matxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_matxtActionPerformed

    private void matxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_matxtFocusLost
        try {
            boolean ma = !valid.validateMat(matxt.getText());
            if (ma) {
                matxt.setBorder(BorderFactory.createLineBorder(Color.red));
            } else {
                matxt.setBorder(null);
            }
            boolean mat = valid.validateMatNum(matxt.getText());
            if (mat) {
                JOptionPane.showMessageDialog(null, "Matric No. already exist");
                matxt.setText("");
            }

        } catch (ClassNotFoundException ex) {

        }


    }//GEN-LAST:event_matxtFocusLost

    private void adtxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_adtxtFocusLost
        boolean ad = !valid.validateAdd(adtxt.getText());
        if (ad) {
            adtxt.setBorder(BorderFactory.createLineBorder(Color.red));
        } else {
            adtxt.setBorder(null);
        }

    }//GEN-LAST:event_adtxtFocusLost

    private void dobFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dobFocusLost
        boolean da = !valid.validateDate(((JTextField) dob.getDateEditor().getUiComponent()).getText());
        if (da) {
            dob.setBorder(BorderFactory.createLineBorder(Color.red));
        } else {
            dob.setBorder(null);
        }

    }//GEN-LAST:event_dobFocusLost

    private void wetxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_wetxtFocusLost
        boolean we = !valid.validateWH(wetxt.getText());
        if (we) {
            wetxt.setBorder(BorderFactory.createLineBorder(Color.red));
        } else {
            wetxt.setBorder(null);
        }

    }//GEN-LAST:event_wetxtFocusLost

    private void hitxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_hitxtFocusLost
        boolean he = !valid.validateWH(hitxt.getText());
        if (he) {
            hitxt.setBorder(BorderFactory.createLineBorder(Color.red));
        } else {
            hitxt.setBorder(null);
        }

    }//GEN-LAST:event_hitxtFocusLost

    private void facomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facomActionPerformed
        int myfaculty = facom.getSelectedIndex();
        switch (myfaculty) {
            case 0:
                DefaultComboBoxModel model = new DefaultComboBoxModel(artcos);
                decom.setMaximumRowCount(artcos.length);
                decom.setModel(model);
                break;
            case 1:
                DefaultComboBoxModel model1 = new DefaultComboBoxModel(scicos);
                decom.setMaximumRowCount(scicos.length);
                decom.setModel(model1);
                break;
        }
    }//GEN-LAST:event_facomActionPerformed

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
            java.util.logging.Logger.getLogger(Registration1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registration1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registration1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registration1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Registration1().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Registration1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField adtxt;
    private javax.swing.JComboBox<String> blcom;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> decom;
    private javax.swing.JLabel dislbl;
    private com.toedter.calendar.JDateChooser dob;
    private javax.swing.JComboBox<String> facom;
    private javax.swing.JRadioButton ferd;
    private javax.swing.JTextField fntxt;
    private javax.swing.JComboBox<String> gecom;
    private javax.swing.JTextField hitxt;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTextField lntxt;
    private javax.swing.JRadioButton mard;
    private javax.swing.JTextField matxt;
    private javax.swing.JTextField patxt;
    private javax.swing.JButton stabtn;
    private javax.swing.JButton stobtn;
    private javax.swing.JTextField wetxt;
    // End of variables declaration//GEN-END:variables
}
