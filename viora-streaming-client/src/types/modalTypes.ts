export const ModalTypes = {
  networkErrorModal: 'networkErrorModal',
  confirmEmailModal: 'confirmEmailModal',
  deleteAccountModal: 'deleteAccountModal',
}

export interface ModalProps {
  data: unknown;
  onClose: () => void;
}

export type ModalTypes = typeof ModalTypes[keyof typeof ModalTypes];