#Region ;**** Directives created by AutoIt3Wrapper_GUI ****
#AutoIt3Wrapper_Outfile_x64=SavePrintOutputAs.Exe
#EndRegion ;**** Directives created by AutoIt3Wrapper_GUI ****
Send("{ENTER}")
Sleep(2000)
ControlFocus("Save Print Output As","","Edit1")
ControlSetText("Save Print Output As","","Edit1", StringLeft(@WorkingDir, StringLen(@WorkingDir)) & "\outputs\files\Report.pdf")
Sleep(1000)
ControlClick("Save Print Output As","","Button2")