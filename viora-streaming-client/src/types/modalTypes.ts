export const ModalTypes = {
  networkErrorModal: 'networkErrorModal',
  confirmEmailModal: 'confirmEmailModal'
}

export interface ModalProps {
  data: unknown;
  onClose: () => void;
}

export type ModalTypes = typeof ModalTypes[keyof typeof ModalTypes];