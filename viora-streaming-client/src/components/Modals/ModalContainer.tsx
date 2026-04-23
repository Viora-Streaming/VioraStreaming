import {type ModalProps, ModalTypes} from "../../types/modalTypes.ts";
import {closeModal, ModalsSelectors} from "../../store/modals.ts";
import {useDispatch, useSelector} from "react-redux";
import {EmailVerificationModal} from "./EmailVerificationModal/EmailVerificationModal.tsx";
import * as React from "react";
import {useCallback} from "react";
import {NetworkErrorModal} from "./NetworkErrorModal/NetworkErrorModal.tsx";

const ModalsMap = new Map<ModalTypes, React.ComponentType<ModalProps>>([
  [ModalTypes.confirmEmailModal, EmailVerificationModal],
  [ModalTypes.networkErrorModal, NetworkErrorModal],
]);

function ModalContainer() {
  const dispatch = useDispatch();
  const modalsStack = useSelector(ModalsSelectors.getModalsStack);

  const onClose = useCallback(() => {
    dispatch(closeModal());
  }, [dispatch]);

  return (
      <>
        {modalsStack.map(({data, id, type}) => {
          const Modal = ModalsMap.get(type);

          if (Modal) {
            return <Modal key={id} data={data} onClose={onClose}/>;
          }

          throw new Error(`Modal type "${type}" is not supported`);
        })}
      </>
  );
}

export default ModalContainer;
