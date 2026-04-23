import type {ModalTypes} from "../types/modalTypes.ts";
import {createSlice, type PayloadAction} from "@reduxjs/toolkit";

type Modal = {
  data: unknown;
  id: string;
  type: ModalTypes;
};

interface ModalsState {
  stack: Modal[];
}

const modalsSlice = createSlice({
  name: 'modalsSlice',
  initialState: {
    stack: [],
  } as ModalsState,
  reducers: {
    openModal(state, action: PayloadAction<Modal>) {
      state.stack.push(action.payload);
    },
    closeModal(state) {
      state.stack.pop();
    },
  },
});


export const getModalsStack = (state: { modal: ModalsState }) => state.modal.stack;
export const getCurrentModal = (state: { modal: ModalsState }) =>
  state.modal.stack[state.modal.stack.length - 1] || null;

export const ModalsSelectors = {
  getModalsStack,
  getCurrentModal,
};

export const {openModal, closeModal} = modalsSlice.actions;
export default modalsSlice.reducer;