import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import config from './store/config'
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { faStar, faChevronLeft, faChevronRight, faBell, faArrowLeft, faList, faDownload, faTrashAlt, faTimes } from '@fortawesome/free-solid-svg-icons'
import { faUserCircle as farUserCircle, faCommentDots as farCommentDots, faSave as farSave, } from '@fortawesome/free-regular-svg-icons'
import VModal from 'vue-js-modal'

Vue.use(VModal)
library.add(farUserCircle, farCommentDots, faStar, faChevronLeft, faChevronRight, faBell, faArrowLeft, faList, farSave, faDownload, faTrashAlt, faTimes)
Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  async beforeCreate() {
    if (typeof (Storage) === 'undefined') {
      console.error('로컬스토리지를 사용 할 수 없습니다.');
      return;
    }

    if (sessionStorage.hasOwnProperty('email')) {
      let email = sessionStorage.getItem('email');
      let nickname = sessionStorage.getItem('nickname');
      let role = sessionStorage.getItem('role');
      let id = sessionStorage.getItem('id')

      config.access_token = sessionStorage.getItem('accessToken');
      config.refresh_token = sessionStorage.getItem('refreshToken');

      this.$store.commit('setUserInfo', { email: email, nickname: nickname, role: role, id: id, accessToken: config.access_token, refreshToken: config.refresh_token });
    }
  },
  render: h => h(App),
}).$mount('#app')
