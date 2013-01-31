//package temp;
//
//import java.io.File;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * writeme: Should be the description of the class
// *
// * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
// */
//
//public class UsePat {
//
//
//	public static void main(String[] args) {
//		String dirName = "AvatarDVDRip. 45BDRip";
//		UsePat up = new UsePat();
//
//		System.out.println(up.modifeName(dirName));
//	}
//	public String modifeName(String fileName){
//		String name;
//		Pattern p2 = Pattern.compile("XviD|DVDRip|BDRip");
//		Matcher m2 = p2.matcher(fileName);
//		if (m2.find()) {
//	  	name = m2.replaceAll(" ");
//			return name;
//		}    else name = fileName;
//		return name;
//	}
//
//
//	public String getFileName(File childFile) {
//			String fileName = childFile.getName();
//			int i = fileName.lastIndexOf('.');
//			if (i != -1) {
//				fileName = fileName.substring(0, i);
//			}
//			return fileName;
//		}
//}
