import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

let store = new Vuex.Store({
  state:{
    dialogueLogin: '',
    dialogueSignUp: '',
  },
  mutations:{
    openDialogueLogin(state) {
      state.dialogueLogin = true
    },
    closeDialogueLogin(state) {
      state.dialogueLogin = false
    },
    openDialogueSignUp(state) {
      state.dialogueSignUp = true
    },
    closeDialogueSignUp(state) {
      state.dialogueSignUp = false
      console.log('mutation')
   }},
  actions: {
    openDialogueLogin(context) {
      context.commit('openDialogueLogin')
    },
    closeDialogueLogin(context) {
      context.commit('closeDialogueLogin')
    },
    openDialogueSignUp(context) {
      context.commit('openDialogueSignUp')
    },
    closeDialogueSignUp(context) {
      context.commit('closeDialogueSignUp')
      console.log('action')
    }
  }
})

global.store = store

export default store
