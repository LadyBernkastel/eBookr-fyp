package uk.co.bernkastel.ebook.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import uk.co.bernkastel.ebook.model.Bookmark;

public class BookmarkItemPanel extends Observable {
	public static final int BOOKMARK_ITEM_WIDTH_DIFF = 62;
	public static final int BOOKMARK_ITEM_HEIGHT = 50;
	private final JPanel panel = new JPanel();
	private JLabel previewLabel;
	private JLabel nameLabel;
	private JLabel dateLabel;
	private Bookmark bookmark;
	private boolean selected = false;
	private Dimension size = new Dimension(288, 50);

	public BookmarkItemPanel(Bookmark bookmark) {
		this.bookmark = bookmark;
		
		panel.setPreferredSize(size);
		panel.setMinimumSize(panel.getPreferredSize());
		panel.setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED,
				null, null), new EmptyBorder(5, 5, 5, 5)));
		panel.setLayout(new BorderLayout(0, 3));

		if (bookmark.getName() != null)
			nameLabel = new JLabel(bookmark.getName());
		else
			nameLabel = new JLabel("");
		panel.add(nameLabel, BorderLayout.WEST);

		if (bookmark.getPreview() != null)
			previewLabel = new JLabel(bookmark.getPreview());
		else
			previewLabel = new JLabel("No preview.");
		previewLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		panel.add(previewLabel, BorderLayout.SOUTH);

		if (bookmark.getDateAdded() != null)
			dateLabel = new JLabel(bookmark.getDateAdded().toString());
		else
			dateLabel = new JLabel("No date");
		dateLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		panel.add(dateLabel, BorderLayout.EAST);

		panel.addMouseListener(new ClickMouseAdapter());

	}
	
	public JPanel getPanel() {
		return panel;
	}

	public void setName(String name) {
		bookmark.setName(name);
		if (nameLabel != null)
			nameLabel.setText(name);
	}

	public Bookmark getBookmark() {
		return bookmark;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;	

		panel.setBackground(Color.WHITE);
		panel.setOpaque(selected);
		panel.revalidate();
		panel.repaint();
	}

	private class ClickMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent arg0) {
			setSelected(!selected);
			setChanged();
			notifyObservers(selected);
		}
	}
}
