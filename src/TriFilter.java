/*---------------------------------------------------------------------------*/
/*                     Copyright (C) 2003  Viacheslav Pozdniakov             */
/*                                   TriGraph 2.5                            */
/*            This program draws graphs based on Afunction(Bx+C)+D           */
/*                            quation where function is                      */
/*                    'sin','cos','tg'('tan') or 'ctg'('cotan')              */
/*                Please read COPYRIGHT file in program's directory          */
/*									     */
/*This program is free software; you can redistribute it and/or modify	     */
/*it under the terms of the GNU General Public License as published by       */
/*the Free Software Foundation; either version 2 of the License, or	     */	
/*(at your option) any later version.                                        */  
/*									     */
/*This program is distributed in the hope that it will be useful,            */
/*but WITHOUT ANY WARRANTY; without even the implied warranty of	     */
/*MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the		     */
/*GNU General Public License for more details.				     */
/*									     */
/*You should have received a copy of the GNU General Public License	     */
/*along with this program; if not, write to the Free Software		     */
/*Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  */
/*									     */
/*It also important to know that only this file and Java classes are         */
/*under the terms of the GNU GPL. Binaries of JRE are distributed under terms*/
/*of license which you can get in Java directory.			     */
/*---------------------------------------------------------------------------*/


import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;


public class TriFilter extends FileFilter {

	String ext = null;   
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

   

        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        
      
	if (ext != null) {
            if (ext.equals("trg")) {
                    return true;
            } else {
                return false;
            }
    }

        return false;
    }
   
    // The description of this filter
    public String getDescription() {
        return "TriGraph files";
    }
}