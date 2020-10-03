properties([
    parameters([string(defaultValue: '', description: 'provide node IP', name: 'node', trim: true)
        ])
    ])

node {
    stage("pull repo"){
        sh """
        rm -rf ansible-melodi
        git clone https://github.com/ikambarov/ansible-melodi.git
        
        """

    }
    withCredentials([sshUserPrivateKey(credentialsId: 'jenkins-master', keyFileVariable: 'SSHKEY', passphraseVariable: '', usernameVariable: 'SSHUSERNAME')]) {
        stage("install Melodi"){
            sh """
                export ANSIBLE_HOST_KEY_CHECKING=False
                ansible-playbook -i "${params.node}" --private-key $SSHKEY ansible-melodi/main.yml -b -u $SSHUSERNAME

            """
        }
    }      
}