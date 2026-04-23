export enum ModalTypes {
  networkErrorModal = 'networkErrorModal',
  confirmEmailModal = 'confirmEmailModal',
}

export interface ModalProps {
  data: unknown;
  onClose: () => void;
}