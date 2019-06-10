#Region ;**** Directives created by AutoIt3Wrapper_GUI ****
#AutoIt3Wrapper_Outfile_x64=SavePrintOutputAs.Exe
#EndRegion ;**** Directives created by AutoIt3Wrapper_GUI ****
Sleep(1000)
ControlFocus("Save Print Output As","","Edit1")
ControlSetText("Save Print Output As","","Edit1", StringLeft(@WorkingDir, StringLen(@WorkingDir) - 6) & "outputs\files\Report.pdf")
Sleep(500)
ControlClick("Save Print Output As","","Button2")