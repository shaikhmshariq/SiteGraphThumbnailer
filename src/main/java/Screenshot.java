/****************************************************************************
 **
 **  (C) 1992-2009 Nokia Corporation and/or its subsidiary(-ies).
** All rights reserved.
 **
 ** This file is part of Qt Jambi.
 **
 ** 
** Commercial Usage
** Licensees holding valid Qt Commercial licenses may use this file in
** accordance with the Qt Commercial License Agreement provided with the
** Software or, alternatively, in accordance with the terms contained in
** a written agreement between you and Nokia.
** 
** GNU Lesser General Public License Usage
** Alternatively, this file may be used under the terms of the GNU Lesser
** General Public License version 2.1 as published by the Free Software
** Foundation and appearing in the file LICENSE.LGPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU Lesser General Public License version 2.1 requirements
** will be met: http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html.
** 
** In addition, as a special exception, Nokia gives you certain
** additional rights. These rights are described in the Nokia Qt LGPL
** Exception version 1.0, included in the file LGPL_EXCEPTION.txt in this
** package.
** 
** GNU General Public License Usage
** Alternatively, this file may be used under the terms of the GNU
** General Public License version 3.0 as published by the Free Software
** Foundation and appearing in the file LICENSE.GPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU General Public License version 3.0 requirements will be
** met: http://www.gnu.org/copyleft/gpl.html.
** 
** If you are unsure which license is appropriate for your use, please
** contact the sales department at qt-sales@nokia.com.
 **
 ** This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 ** WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 **
 ****************************************************************************/



import com.trolltech.qt.core.QDir;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.core.Qt.AlignmentFlag;
import com.trolltech.qt.core.Qt.AspectRatioMode;
import com.trolltech.qt.core.Qt.TransformationMode;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QCheckBox;
import com.trolltech.qt.gui.QFileDialog;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QResizeEvent;
import com.trolltech.qt.gui.QSizePolicy.Policy;
import com.trolltech.qt.gui.QSpinBox;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

//@QtJambiExample(name = "Screenshot")
public class Screenshot extends QWidget {

    public static void main(String args[]) {
        QApplication.initialize(args);
        Screenshot screenshot = new Screenshot(null);
        screenshot.show();
        QApplication.exec();
    }

    volatile QPixmap originalPixmap;

    QLabel screenshotLabel;
    QGroupBox optionsGroupBox;
    QSpinBox delaySpinBox;
    QLabel delaySpinBoxLabel;
    QCheckBox hideThisWindowCheckBox;
    QPushButton newScreenshotButton;
    QPushButton saveScreenshotButton;
    QPushButton quitScreenshotButton;

    QVBoxLayout mainLayout;
    QGridLayout optionsGroupBoxLayout;
    QHBoxLayout buttonsLayout;

    public Screenshot(QWidget parent) {
        super(parent);
        screenshotLabel = new QLabel();
        screenshotLabel.setSizePolicy(Policy.Expanding, Policy.Expanding);
        screenshotLabel.setAlignment(AlignmentFlag.AlignCenter);
        screenshotLabel.setMinimumSize(240, 160);

        createOptionsGroupBox();
        createButtonsLayout();

        mainLayout = new QVBoxLayout();
        mainLayout.addWidget(screenshotLabel);
        mainLayout.addWidget(optionsGroupBox);
        mainLayout.addLayout(buttonsLayout);
        setLayout(mainLayout);

        shootScreen();
        delaySpinBox.setValue(5);

        setWindowIcon(new QIcon("classpath:com/trolltech/images/qt-logo.png"));
        setWindowTitle(tr("Screenshot"));
        resize(300, 200);
    }

    @Override
    public void resizeEvent(QResizeEvent event) {
        QSize scaledSize = originalPixmap.size();
        scaledSize.scale(screenshotLabel.size(),
                         AspectRatioMode.KeepAspectRatio);
        if (screenshotLabel.pixmap() != null
            || scaledSize != screenshotLabel.pixmap().size())
                updateScreenshotLabel();
    }

    void newScreenshot() {
        if (hideThisWindowCheckBox.isChecked())
            hide();
        newScreenshotButton.setDisabled(true);

        QTimer.singleShot(delaySpinBox.value() * 1000,
                          this, "shootScreen()");
    }

    void saveScreenshot() {
        String format = "png";
        String initialPath = QDir.currentPath() + tr("/untitled.") + format;
        String filter = String.format(tr("%1$s Files (*.%2$s);;All Files (*)"),
                                      format.toUpperCase(), format);
        String fileName;
        fileName = QFileDialog.getSaveFileName(this, tr("Save As"), initialPath,
                                               new QFileDialog.Filter(filter));

        if (!fileName.equals(""))
            originalPixmap.save(fileName, format);
    }

    void shootScreen() {
        if (delaySpinBox.value() != 0)
            QApplication.beep();

        originalPixmap = null;

        originalPixmap = QPixmap.grabWindow(
                QApplication.desktop().winId());
        updateScreenshotLabel();

        newScreenshotButton.setDisabled(false);
        if (hideThisWindowCheckBox.isChecked())
            show();
    }

    void updateCheckBox() {
        if (delaySpinBox.value() == 0)
            hideThisWindowCheckBox.setDisabled(true);
        else
            hideThisWindowCheckBox.setDisabled(false);
    }

    void createOptionsGroupBox() {
        optionsGroupBox = new QGroupBox(tr("Options"));

        delaySpinBox = new QSpinBox();
        delaySpinBox.setSuffix(tr(" s"));
        delaySpinBox.setMaximum(60);
        delaySpinBox.valueChanged.connect(this, "updateCheckBox()");

        delaySpinBoxLabel = new QLabel(tr("Screenshot Delay:"));

        hideThisWindowCheckBox = new QCheckBox(tr("Hide This Window"));

        optionsGroupBoxLayout = new QGridLayout();
        optionsGroupBoxLayout.addWidget(delaySpinBoxLabel, 0, 0);
        optionsGroupBoxLayout.addWidget(delaySpinBox, 0, 1);
        optionsGroupBoxLayout.addWidget(hideThisWindowCheckBox, 1, 0, 1, 2);
        optionsGroupBox.setLayout(optionsGroupBoxLayout);
    }

    void createButtonsLayout() {
        newScreenshotButton = createButton(tr("New Screenshot"), this,
                                           "newScreenshot()");

        saveScreenshotButton = createButton(tr("Save Screenshot"), this,
                                            "saveScreenshot()");

        quitScreenshotButton = createButton(tr("Quit"), this, "close()");

        buttonsLayout = new QHBoxLayout();
        buttonsLayout.addStretch();
        buttonsLayout.addWidget(newScreenshotButton);
        buttonsLayout.addWidget(saveScreenshotButton);
        buttonsLayout.addWidget(quitScreenshotButton);
    }

    QPushButton createButton(final String text, QWidget receiver,
                             String member) {
        QPushButton button = new QPushButton(text);
        button.clicked.connect(receiver, member);
        return button;
    }

    void updateScreenshotLabel() {
        screenshotLabel.setPixmap(originalPixmap.scaled(screenshotLabel.size(),
                                  AspectRatioMode.KeepAspectRatio,
                                  TransformationMode.SmoothTransformation));
    }
}
