$action = New-ScheduledTaskAction -Execute 'Powershell.exe' -Argument 'PATH\TO\automateEventReminderWindows.ps1' -WorkingDirectory 'PATH\TO\WORKING_DIRECTORY\'

$trigger = New-ScheduledTaskTrigger -Daily -At DESIRED_RUN_TIME

$settings = New-ScheduledTaskSettingsSet -WakeToRun

Register-ScheduledTask -Action $action -Trigger $trigger -TaskName "Email Update Service" -Description "Daily News and Events Email" -Settings $settings