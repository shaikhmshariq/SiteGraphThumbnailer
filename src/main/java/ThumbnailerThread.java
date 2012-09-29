import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QImage;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.webkit.QWebPage;

public class ThumbnailerThread extends QObject implements Runnable {
	public Signal0 finished = new Signal0();
	private int width = 218;
	private int height = 128;
	private QWebPage page;
	private String url;

	public ThumbnailerThread(String url) {
		this.url = url;
	}

	@Override
	public void run() {

		page = new QWebPage();
		page.loadStarted.connect(this, "loadStarted()");

		page.loadFinished.connect(this, "render(Boolean)");
		page.mainFrame().load(new QUrl(url));
	}

	public void loadStarted() {
		System.out.println("load started");
	}

	public void render(Boolean b) {
		System.out.println("rendering now : "
				+ page.mainFrame().contentsSize().toString());
		// if the page is long, it may take long time.
		// for example, http://doc.trolltech.com/qq/qq20-jambi.html has a size
		// of 620x54502

		page.setViewportSize(page.mainFrame().contentsSize());

		QImage image = new QImage(page.viewportSize(),QImage.Format.Format_ARGB32);
		QPainter painter = new QPainter(image);

		page.mainFrame().render(painter);
		painter.end();

		// we assume that the page width is smaller than the page height
		QImage thumbnail = image.scaledToWidth(width,
				Qt.TransformationMode.SmoothTransformation);
		thumbnail.save("thumbnail.png");

		finished.emit();

		System.out.println("rendering finished");
	}
}