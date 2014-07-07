package uk.co.bernkastel.ebook.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uk.co.bernkastel.ebook.controller.Controller;
import uk.co.bernkastel.ebook.model.Format;
import uk.co.bernkastel.ebook.view.book.BookView;

public class MainWindowView extends Observable {

	public static final int MIN_CONTENT_WIDTH = 620;
	public static final int MIN_CONTENT_HEIGHT = 810;
	private static final int WIDTH_PADDING = 10;
	private static final int HEIGHT_PADDING = 110;
	public static final int MIN_TOTAL_WIDTH = WIDTH_PADDING + MIN_CONTENT_WIDTH;
	public static final int MIN_TOTAL_HEIGHT = HEIGHT_PADDING + MIN_CONTENT_HEIGHT;
	public static int contentWidth = MIN_CONTENT_WIDTH;
	public static int contentHeight = MIN_CONTENT_HEIGHT;
	private int borderPadding = 5;

	private boolean pageable = false;

	private JFrame mainWindow;
	private BookView bookView;
	private JPanel contentArea;

	public MainWindowView() {
		mainWindow = new JFrame("eBookr - eBook Reader");
		contentArea = new JPanel();

		initMainWindow();
		createMenuBar();	

		Toolkit.getDefaultToolkit().setDynamicLayout(false);

		mainWindow.addComponentListener(new ResizeListener());
		mainWindow.addWindowStateListener(new MainWindowListener());
		mainWindow.setResizable(true);
		mainWindow.setVisible(true);
	}

	private void initMainWindow() {
		mainWindow.setSize(MIN_TOTAL_WIDTH, MIN_TOTAL_HEIGHT);
		mainWindow.setMinimumSize(new Dimension(MIN_TOTAL_WIDTH, MIN_TOTAL_HEIGHT));
		contentArea.setPreferredSize(new Dimension(MIN_CONTENT_WIDTH, MIN_CONTENT_HEIGHT));
		contentArea.setBorder(new EmptyBorder(borderPadding, borderPadding, borderPadding, borderPadding));
		mainWindow.add(contentArea, BorderLayout.CENTER);
		mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void createMenuBar() {
		MainMenuBar menuBar = new MainMenuBar();
		mainWindow.setJMenuBar(menuBar);
		addObserver(menuBar);
	}

	public void setBookView(BookView view) {
		if (bookView != null)
			contentArea.removeAll();

		if (view != null) {
			bookView = view;
			contentArea.add(bookView.getViewComponent(), BorderLayout.CENTER);
		}

		for (Format f : view.getFormatsAccepted()) {
			if (f.isPageable()) {
				setPageable(true);
			}
			else {
				setPageable(false);
			}
		}

		mainWindow.revalidate();
		mainWindow.repaint();
	}

	public BookView getBookView() {
		return bookView;
	}

	public static Dimension getContentSize() {
		return new Dimension(contentWidth, contentHeight);
	}

	private void onResize() {
		contentWidth = mainWindow.getWidth() - WIDTH_PADDING;
		contentHeight = mainWindow.getHeight() - HEIGHT_PADDING;
		contentArea.setPreferredSize(getContentSize());
		if (bookView != null)
			bookView.updateContentSize(getContentSize());
		mainWindow.revalidate();
		mainWindow.repaint();
	}

	public boolean isPageable() {
		return pageable;
	}

	public void setPageable(boolean pageable) {
		if (this.pageable != pageable) {
			this.pageable = pageable;
			setChanged();
			notifyObservers(pageable);
		}
	}

	private class ResizeListener extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			onResize();
		}
	}

	private class MainWindowListener extends WindowAdapter {
		public void windowClosed(WindowEvent e) {
			Controller.getInstance().applicationExit();
		}

		public void windowStateChanged(WindowEvent arg0) {
			onResize();
		}
	}

	public void setTitle(String string) {
		mainWindow.setTitle(string);
	}

	public JFrame getFrame() {
		return mainWindow;
	}
}
