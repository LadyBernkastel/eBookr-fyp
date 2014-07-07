package uk.co.bernkastel.ebook.view.book;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import uk.co.bernkastel.ebook.controller.BookmarkManager;
import uk.co.bernkastel.ebook.model.Format;
import uk.co.bernkastel.ebook.model.book.Ebook;

public abstract class BookView {

	private JComponent viewComponent;

	protected boolean checkFormatAccepted(Ebook ebook) {
		boolean accepted = false;
		for (Format format : getFormatsAccepted())
			if (ebook.getFormats().contains(format))
				accepted = true;
		return accepted;
	}

	public void setViewComponent(JComponent j) {
		viewComponent = j;
		addPopupMenu();
	}

	public JComponent getViewComponent() {
		return viewComponent;
	}

	public abstract void setContent(Ebook ebook);

	public abstract List<Format> getFormatsAccepted();

	public abstract void goToBeginning();

	public abstract void goToEnd();

	public abstract int getSelectedStart();

	public abstract int getSelectedEnd();

	public abstract void goToLocation(int location);

	public abstract String getSelectedText();

	/**
	 * Used to update the content size of the view when the Main Window is
	 * resized. Should always call revalidate and repaint after undergoing the
	 * resize.
	 * 
	 * @param di
	 */
	public abstract void updateContentSize(Dimension di);
	
	protected void addPopupMenu() {
		JPopupMenu menu = new JPopupMenu();
		JMenuItem item = new JMenuItem("Add Bookmark");
		item.addMouseListener(new PopupMenuMouseAdapter());
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BookmarkManager.getInstance().createBookmarkDialog();
			}
		});
		menu.add(item);
		getViewComponent().setComponentPopupMenu(menu);
	}

	/**
	 * BookViewMouseAdapter is used for the displaying/hiding of the popup menu
	 * on a book view.
	 * 
	 * @author racha_000
	 * 
	 */
	protected class BookViewMouseAdapter extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent arg0) {
			checkMouseEvent(arg0);
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			checkMouseEvent(arg0);
		}

		private void checkMouseEvent(MouseEvent e) {
			JPopupMenu menu = getViewComponent().getComponentPopupMenu();
			if (e.isPopupTrigger()) {
				menu.setLocation(e.getXOnScreen(), e.getYOnScreen());
				menu.setVisible(true);
			} else {
				menu.setVisible(false);
			}
		}
	}

	/**
	 * PopupMenuMouseAdapter determines when the user's mouse has left the popup
	 * menu area so that the menu can be hidden again.
	 * 
	 * @author racha_000
	 * 
	 */
	private class PopupMenuMouseAdapter extends MouseAdapter {
		@Override
		public void mouseExited(MouseEvent e) {
			if (getViewComponent().getComponentPopupMenu().isVisible()) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				if (!getViewComponent().getComponentPopupMenu().getBounds()
						.contains(x, y)) {
					getViewComponent().getComponentPopupMenu()
							.setVisible(false);
				}
			}
			super.mouseExited(e);
		}

	}
}
