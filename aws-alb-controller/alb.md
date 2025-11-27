aws iam create-policy --policy-name AWSLoadBalancerControllerIAMPolicy --policy-document file://iam_policy.json

aws eks describe-cluster --name eks-cluster-1-34 --query "cluster.identity.oidc.issuer" --output text 


aws iam create-role   --role-name AmazonEKSLoadBalancerControllerRole  --assume-role-policy-document file://"load-balancer-role-trust-policy.json"

aws iam attach-role-policy  --policy-arn arn:aws:iam::150916258276:policy/AWSLoadBalancerControllerIAMPolicy   --role-name AmazonEKSLoadBalancerControllerRole


## Install Cert-manager

kubectl apply --validate=false -f https://github.com/jetstack/cert-manager/releases/download/v1.13.5/cert-manager.yaml