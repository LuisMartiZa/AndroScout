/**
   Copyright Mob&Me 2013 (@MobAndMe)

   Licensed under the GPL General Public License, Version 3.0 (the "License"),  
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.gnu.org/licenses/gpl.html

   Unless required by applicable law or agreed to in writing, software 
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   
   Website: http://adaframework.com
   Contact: Txus Ballesteros <txus.ballesteros@mobandme.com>
*/

/**
 * Custom Date parser, this class format and parse dates to get and put from Screen.
 * @author   Txus Ballesteros (@DesAndrOId)
 * @category Custom Parsers
 * @version  2.3
 */

package com.example.scoutmanager.parsers;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mobandme.ada.DataBinder;
import com.mobandme.ada.DataParser;

import android.annotation.SuppressLint;

public class DateParser extends DataParser {

	@SuppressLint("SimpleDateFormat")
	@Override
	public Object parseValue(Object pOriginalValue, int pDirection) {
		Object returnedValue = pOriginalValue;
		
		try {
			
			if (pOriginalValue != null) {
				if (pDirection == DataBinder.BINDING_UI_TO_ENTITY) {
					if (pOriginalValue instanceof String) {
						returnedValue = new SimpleDateFormat("yyyy-MM-dd").parse((String)pOriginalValue);
					}
				} else {
					if (pOriginalValue instanceof Date) {
						returnedValue = new SimpleDateFormat("yyyy-MM-dd").format(pOriginalValue);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnedValue;
	}
}
