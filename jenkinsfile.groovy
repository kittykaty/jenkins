properties([
pipelineTriggers([])
])

node()
{
    stage("name_of_stage")
    {
        sh"uptime"
        deleteDir()
        
    }
    stage("gitclone")
    {
        withCredentials([usernamePassword(credentialsId: '8ac4c009-83a1-4c60-9dae-dd0f7678c245', passwordVariable: 'password', usernameVariable: 'username')]) 
        {
            sh"git clone https://$username:$password@github.com/kittykaty/ansible_test.git"
        }
    }
}
    input("Please, allow to deploy")
    
    node(){
    
    stage("another_stage")
    {
        def workDir=sh(returnStdout:true,script:"pwd").trim()
        sh"cd ${workDir} && cd ./ansible_test && ls -lh"
    }
    stage("archive")
    {
        archiveArtifacts artifacts:"ansible_test/**/nginx_install.yml",fingerprint:true
    }
    
}
