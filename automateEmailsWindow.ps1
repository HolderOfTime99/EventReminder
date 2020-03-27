$action = New-ScheduledTaskAction -Execute 'Powershell.exe' -Argument 'path\to\file' -WorkingDirectory 'path\to\working\directory'

$trigger = New-ScheduledTaskTrigger -Daily -At desired_time

$settings = New-ScheduledTaskSettingSet -WakeToRun

Register-ScheduledTask -Action $action -Trigger $trigger -TaskName "Email Update Service -Description "Daily News and Events Email" -Settings $settings