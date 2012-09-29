import com.sitegraph.core.pdf.PdfThumbnailer;
import com.sitegraph.core.pdf.impl.PdfThumbnailerImpl;
import com.trolltech.qt.QSignalEmitter.Signal0;
import com.trolltech.qt.core.QObject;

public class I extends QObject{
	public Signal0 finished = new Signal0();
public PdfThumbnailer getPdfThumbnailer(){
		
		System.out.println(Thread.currentThread());
		return new PdfThumbnailerImpl(Thread.currentThread());
		
	}
}