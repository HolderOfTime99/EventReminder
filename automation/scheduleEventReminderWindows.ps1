
#########   USER VARIABLES   ###########
$runtime = DESIRED_RUN_TIME

$pathTo = 'PATH\TO\automateEventReminderWindows.ps1'

$workingDirectory = 'PATH\TO\WORKING_DIRECTORY\'
#########################################

$action = New-ScheduledTaskAction -Execute 'Powershell.exe' -Argument $pathTo -WorkingDirectory $workingDirectory

$trigger = New-ScheduledTaskTrigger -Daily -At $runtime

$settings = New-ScheduledTaskSettingsSet -WakeToRun

Register-ScheduledTask -Action $action -Trigger $trigger -TaskName "Email Update Service" -Description "Daily News and Events Email" -Settings $settings