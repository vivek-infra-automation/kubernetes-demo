aws iam create-role  --role-name AmazonEKS_EBS_CSI_DriverRole-eks-cluster-1-34 --assume-role-policy-document file://"C:\Users\vivek\kubernetes\ebs-csi-driver\ebs-csi-driver-trust-policy.json"


aws iam attach-role-policy --policy-arn arn:aws:iam::aws:policy/service-role/AmazonEBSCSIDriverPolicy --role-name AmazonEKS_EBS_CSI_DriverRole-eks-cluster-1-34

aws eks create-addon --cluster-name eks-cluster-1-34 --addon-name aws-ebs-csi-driver --service-account-role-arn arn:aws:iam::150916258276:role/AmazonEKS_EBS_CSI_DriverRole-eks-cluster-1-34