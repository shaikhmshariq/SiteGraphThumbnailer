
import java.util.Random;

import com.trolltech.qt.*;
import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.webkit.*;

public class W extends QLabel
{
	MyRunnable runnable;
	QThread thread;

	public W()
	{

		runnable = new MyRunnable();
		runnable.SetEvents();
		thread = new QThread(runnable);
	}

	public void InitializeConversion()
	{
		runnable.moveToThread(thread);
		thread.start();
	}

	private class MyRunnable extends QObject implements Runnable
	{

		private QWebPage page;
		private QEventLoop loop;

		public MyRunnable()
		{
			page = new QWebPage(this);
		}

		public void SetEvents()
		{
			page.loadFinished.connect(this, "loadFinished()");
			page.loadProgress.connect(this, "loadProgress(int)");
		}

		public void run()
		{
			try
			{
				page.mainFrame().load(new QUrl("http://www.google.com"));
			}
			catch (Throwable e)
			{
				e.printStackTrace();
			}

			loop = new QEventLoop();
			loop.exec();
		}

		public void loadProgress(final int progress)
		{
			QApplication.invokeLater(new Runnable()
			{
				public void run()
				{
					setText(progress + "%");
				}
			});
		}

		public void loadFinished()
		{
			Random objRandom = new Random();

			page.setViewportSize(page.mainFrame().contentsSize());

			QPrinter printer = new QPrinter();
			printer.setOutputFileName("C:/temp/"
					+ objRandom.nextInt() + ".pdf");
			printer.setOutputFormat(QPrinter.OutputFormat.PdfFormat);

			QPainter painter = new QPainter();
			painter.begin(printer);
			page.mainFrame().render(painter);
			painter.end();

			loop.exit();

			QApplication.invokeLater(new Runnable()
			{
				public void run()
				{
					close();
				}
			});
		}
	}

	public static void main(String args[])
	{
		QApplication.initialize(args);

		QWidget widget = new W();
		// widget.show();
		QWidget widget2 = new W();
		// widget2.show();

		// Case 1
		QApplication.exec();

		((W)widget).InitializeConversion();
		((W)widget2).InitializeConversion();
		//Case 1 ends

		// Case 2
		//QApplication.exec();

		//((WebFrameInThread)widget).InitializeConversion();
		//((WebFrameInThread)widget2).InitializeConversion();
		//Case 2 ends

	}
}
