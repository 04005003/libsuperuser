/*
 * Copyright (C) 2012 Jorrit "Chainfire" Jongma
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.en.dis.ownapps;

import android.app.*;
import android.os.*;
import eu.chainfire.libsuperuser.*;
import java.util.*;

public class MainActivity extends Activity 
{		
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		openSUDialog();
    }
	
	private static Shell.Interactive rootSession;
	private void reportError(String error)
	{
		List<String> errorInfo = new ArrayList<String>();
		errorInfo.add(error);
		rootSession = null;
	}
	private void openSUDialog()
	{
		if (rootSession != null)
		{
			//SU();
			rootSession.addCommand(new String[]
			{
				"su"
			});
		}
		else
		{
			rootSession = new Shell.Builder().useSU().setWantSTDERR(true).setWatchdogTimeout(1).setMinimalLogging(true).open(new Shell.OnCommandResultListener()
			{
				@Override
				public void onCommandResult(int commandCode, int exitCode, List<String> output)
				{ 
				if (exitCode != Shell.OnCommandResultListener.SHELL_RUNNING){reportError("Error opening root shell: exitCode " + exitCode);
				}
						//else {}
				}
			});
		}
	}
	/*private void SU()
	{
		rootSession.addCommand(new String[]
		{
			"su"
		});
	}*/
	
	
}


