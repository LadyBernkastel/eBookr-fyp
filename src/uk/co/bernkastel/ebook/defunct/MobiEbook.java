package uk.co.bernkastel.ebook.defunct;

import java.util.ArrayList;
import java.util.List;

import uk.co.bernkastel.ebook.model.Format;
import uk.co.bernkastel.ebook.model.book.Ebook;

/*
 * THIS CODE IS NOT USED IN THE FINAL PRODUCT. PROVIDED AS EXAMPLE OF ATTEMPT AT MOBI IMPLEMENTATION.
 */
public class MobiEbook extends Ebook {
	private static final long serialVersionUID = 1L;
	private String content;
	private PDBHeader pdbHeader;
	private MobiHeader mobiHeader;
	private List<EXTHRecord> exthRecords;

	@Override
	public List<Format> getFormats() {
		List<Format> list = new ArrayList<Format>();
		//list.add(Format.MOBI);
		return list;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public PDBHeader getPdbHeader() {
		return pdbHeader;
	}

	public void setPdbHeader(PDBHeader pdbHeader) {
		this.pdbHeader = pdbHeader;
	}

	public MobiHeader getMobiHeader() {
		return mobiHeader;
	}

	public void setMobiHeader(MobiHeader mobiHeader) {
		this.mobiHeader = mobiHeader;
	}

	public List<EXTHRecord> getExthRecords() {
		return exthRecords;
	}

	public void setExthRecords(List<EXTHRecord> exthRecords) {
		this.exthRecords = exthRecords;
	}
}
