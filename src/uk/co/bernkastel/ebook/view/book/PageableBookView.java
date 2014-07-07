package uk.co.bernkastel.ebook.view.book;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import uk.co.bernkastel.ebook.view.MainWindowView;
import uk.co.bernkastel.ebook.view.MessageDialog;

public abstract class PageableBookView extends BookView {
	// Page related variables
	private int currentPage = 1;
	protected Map<Integer, BufferedImage> pageImagesMap = new HashMap<Integer, BufferedImage>();
	
	// Layout related Variables;
	protected final int buttonWidth = 50;
	protected final float RATIO = 1.4142f;
	protected final int scrollbarSpacing = 15;
	protected Dimension contentArea = new Dimension(
			(MainWindowView.getContentSize().width - buttonWidth * 2)
					- scrollbarSpacing, MainWindowView.getContentSize().height);
	protected Dimension scaledPage = new Dimension(
			getScaledPageDimension(contentArea));

	/**
	 * Constructor for the book view, lays out the panel and buttons then sets
	 * it as the view component.
	 */
	public PageableBookView() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JButton buttonLeft = getPreviousButton();
		buttonLeft.setSize(buttonWidth, getContentHeight());
		JButton buttonRight = getNextButton();
		buttonRight.setSize(buttonWidth, getContentHeight());

		mainPanel.add(buttonLeft, BorderLayout.WEST);
		mainPanel.add(buttonRight, BorderLayout.EAST);

		setViewComponent(mainPanel);
	}

	/**
	 * Draws the given page to the current view component.
	 * @param pageNo The page number to draw.
	 */
	public void drawPage(int pageNo) {
		getViewComponent().remove(2);
		getViewComponent().add(createScrollPane(pageNo), 2);
		getViewComponent().revalidate();
		getViewComponent().repaint();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public abstract int getNumberOfPages();

	@Override
	public int getSelectedEnd() {
		return getCurrentPage();
	}

	@Override
	public int getSelectedStart() {
		return getCurrentPage();
	}

	/**
	 * Returns the text used when saving bookmarks for this format.
	 */
	@Override
	public String getSelectedText() {
		return "Page number: " + (getSelectedStart());
	}

	public void goToBeginning() {
		currentPage = 1;
		drawPage(currentPage);
	}

	public void goToEnd() {
		currentPage = getNumberOfPages();
		drawPage(currentPage);
	}

	public void goToLocation(int location) {
		if (location < getNumberOfPages() + 1 && location > 0) {
			currentPage = location;
			drawPage(currentPage);
		} else {
			MessageDialog.showErrorDialog("Unable to find page " + location);
		}
	}

	public void nextPage() {
		if (!isLastPage()) {
			currentPage++;
			drawPage(currentPage);
		} else {
			JOptionPane.showMessageDialog(null,
					"You have reached the end of the book");
		}
	}

	public void previousPage() {
		if (!isFirstPage()) {
			currentPage--;
			drawPage(currentPage);
		} else {
			JOptionPane.showMessageDialog(null,
					"You are at the start of the book");
		}
	}

	@Override
	public void updateContentSize(Dimension di) {
		if (getScaledContentArea(di) != contentArea) {
			setContentArea(di);
			scaledPage = getScaledPageDimension(contentArea);
			getViewComponent().setSize(di);
			drawPage(getCurrentPage());
		}
	}

	/**
	 * Creates the scroll pane used to display the paged book when it becomes too large.
	 * @param pageNo
	 * @return
	 */
	protected JScrollPane createScrollPane(int pageNo) {
		JPanel imagePanel = new JPanel();
		ImageIcon imageIcon = getScaledImageIcon(pageNo);
		imagePanel.add(new JLabel(imageIcon), BorderLayout.CENTER);
		JScrollPane jsc = new JScrollPane(imagePanel);
		jsc.setPreferredSize(contentArea);
		jsc.addMouseListener(new BookViewMouseAdapter());
		return jsc;
	}

	protected int getContentHeight() {
		return contentArea.height;
	}

	protected JButton getNextButton() {
		JButton button = new JButton("->");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextPage();
			}
		});
		return button;
	}

	protected int getPageHeight() {
		return scaledPage.height;
	}

	protected int getPageWidth() {
		return scaledPage.width;
	}

	protected JButton getPreviousButton() {
		JButton button = new JButton("<-");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				previousPage();
			}
		});
		return button;
	}

	protected Dimension getScaledContentArea(Dimension di) {
		return new Dimension(di.width - buttonWidth * 2, di.height);
	}

	protected ImageIcon getScaledImageIcon(int pageNo) {
		BufferedImage image;	
		if (!pageImagesMap.isEmpty() && pageImagesMap.containsKey(pageNo)) {
			image = pageImagesMap.get(pageNo);
			return getScaledInstance(image);
		}
		return null;
	}
	
	protected ImageIcon getScaledInstance(BufferedImage image) {
		return new ImageIcon(image.getScaledInstance(getPageWidth(), getPageHeight(), BufferedImage.SCALE_SMOOTH));
	}

	protected Dimension getScaledPageDimension(Dimension di) {
		return new Dimension(di.width - scrollbarSpacing,
				(int) (di.width * RATIO) - scrollbarSpacing);
	}

	protected boolean isFirstPage() {
		if (currentPage == 1)
			return true;
		return false;
	}

	protected boolean isLastPage() {
		if (currentPage == getNumberOfPages())
			return true;
		return false;
	}

	protected void setContentArea(Dimension di) {
		contentArea = new Dimension(di.width - buttonWidth * 2, di.height);
	}

}
