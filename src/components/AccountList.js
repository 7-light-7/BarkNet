import React, { useState, useEffect } from 'react';
import axios from 'axios';

const AccountList = () => {
  const [accounts, setAccounts] = useState([]);
  const [selectedAccount, setSelectedAccount] = useState(null);
  const [workOrders, setWorkOrders] = useState([]);

  useEffect(() => {
    axios.get('/accounts')
      .then(response => {
        setAccounts(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the accounts!', error);
      });
  }, []);

  const handleAccountClick = (account) => {
    setSelectedAccount(account);
    axios.get(`/workorders/account/${account.id}`)
      .then(response => {
        setWorkOrders(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the work orders!', error);
      });
  };

  return (
    <div className="account-list container">
      <h2>Accounts</h2>
      <div className="d-grid gap-2">
        {accounts.map(account => (
          <button
            key={account.id}
            className="btn btn-primary btn-block text-start"
            onClick={() => handleAccountClick(account)}
          >
            {account.name}
          </button>
        ))}
      </div>
      {selectedAccount && (
        <div className="work-orders mt-3">
          <h3>Work Orders for {selectedAccount.name}</h3>
          <ul className="list-group">
            {workOrders.length > 0 ? (
              workOrders.map(workOrder => (
                <li key={workOrder.id} className="list-group-item">{workOrder.description}</li>
              ))
            ) : (
              <li className="list-group-item">No work orders available</li>
            )}
          </ul>
        </div>
      )}
    </div>
  );
};

export default AccountList;
