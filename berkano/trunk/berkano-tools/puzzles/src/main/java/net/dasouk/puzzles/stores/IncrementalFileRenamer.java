package net.dasouk.puzzles.stores;

/**
 * Generates new names for files based on an incremental number.
 * The following rules apply:
 * <ul>
 *      <li>given a filename <i>filename.extension</i>
 *      the returned filename will be <i>filename-1.extension</i></li>
 *      <li>if the filename is already in the form <i>filename-number.extension</i>
 *      then the returned filename will be <i>filename-(number+1).extension</i>, ie, the number is incremented</li>
 * </ul>
 *
 */
public class IncrementalFileRenamer implements FileRenamer{
    public String generateNewName(String filename) {
        String firstPart = filename.substring(0,filename.indexOf("."));
        String secondPart = filename.substring(filename.indexOf("."));
        int lastIndexOfDash = firstPart.lastIndexOf("-");
        if (lastIndexOfDash>0){
            String lastPartAfterDash = firstPart.substring(lastIndexOfDash+1);
            //parse it as an integer
            try {
                int number = Integer.parseInt(lastPartAfterDash);
                return firstPart.substring(0,firstPart.lastIndexOf("-"))+"-"+(number+1)+secondPart;
            } catch (NumberFormatException e) {
                //cannot be parsed as a number
                //continue the normal flow and return the basic name
            }
        }
        return firstPart+"-1"+secondPart;
    }
}
