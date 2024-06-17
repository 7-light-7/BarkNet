import React from 'react';
import { Provider } from 'react-redux';
import store from '../store/store';
import AccountList from './AccountList';

const App = () => (
  <Provider store={store}>
    <div className="container">
      <h1 className="text-center mt-5">Hello, React and Redux!</h1>
      <AccountList />
    </div>
  </Provider>
);

export default App;
