package tiktzuki.e_store.services;

import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

public class LoadingImageService extends SwingWorker<ImageIcon[], Void> {
    JLabel lblImage;
    String imageName;

    public LoadingImageService(JLabel lblImage, String imageName) {
        this.lblImage = lblImage;
        this.imageName = imageName;
    }

    @Override
    protected ImageIcon[] doInBackground() throws Exception {
        return new ImageIcon[]{ImageUtils.loadImage(imageName, 200, 200)};
    }

    @Override
    protected void done() {
        try {
            lblImage.setIcon(get()[0]);
            lblImage.revalidate();
            lblImage.repaint();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
//	    @Override
//	    public ImageIcon[] doInBackground() {
//	        final ImageIcon[] innerImgs = new ImageIcon[nimgs];
//	        for (int i = 0; i < nimgs; i++) {
//	            innerImgs[i] = loadImage(i+1);
//	        }
//	        return innerImgs;
//	    }

    //	    @Override
//	    public void done() {
//	        //Remove the "Loading images" label.
//	        animator.removeAll();
//	        loopslot = -1;
//	        try {
//	            imgs = get();
//	        } catch (InterruptedException ignore) {}
//	        catch (java.util.concurrent.ExecutionException e) {
//	            String why = null;
//	            Throwable cause = e.getCause();
//	            if (cause != null) {
//	                why = cause.getMessage();
//	            } else {
//	                why = e.getMessage();
//	            }
//	            System.err.println("Error retrieving file: " + why);
//	        }
//	    }
    public static void main(String[] args) {

    }
}
