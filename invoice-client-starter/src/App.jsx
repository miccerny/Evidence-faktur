/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */

import "bootstrap/dist/css/bootstrap.min.css";
import {
  BrowserRouter as Router,
  Link,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";

import PersonIndex from "./persons/PersonIndex";
import PersonDetail from "./persons/PersonDetail";
import PersonForm from "./persons/PersonForm";
import InvoiceIndex from "./invoices/InvoiceIndex";
import PurchasesInvoice from "./persons/purchasesAndSales/PurchasesInvoice";
import SalesInvoice from "./persons/purchasesAndSales/SalesInvoice";
import InvoiceDetail from "./invoices/InvoiceDetail";
import InvoiceForm from "./invoices/InvoiceForm";
import PersonStatistics from "./statistics/PersonStatistics";
import InvoiceStatistics from "./statistics/InvoiceStatistics";
import './style.css';

export function App() {
  return (
    <Router>
      <div className="container">
        <div className="dropdown d-flex justify-content-end col-12">
        <nav className="navbar navbar-expand-lg navbar-light ">
          <ul className="navbar-nav mr-auto">
            <li className="nav-item">
              <Link to={"/persons"} className="nav-link">
                Osoby
              </Link>
            </li>
            <li className="nav-item">
              <Link to={"/invoices"} className="nav-link">
                Faktury
              </Link>
            </li>
            <li className="nav-item">
            
            </li>
          </ul>
        </nav>
        </div>

        <Routes>
          <Route index element={<Navigate to={"/persons"} />} />
          <Route path="/persons">
            <Route index element={<PersonIndex />} />
            <Route path="show/:id" element={<PersonDetail />} />
            <Route path="create" element={<PersonForm />} />
            <Route path="edit/:id" element={<PersonForm />} />
          </Route>
          <Route path="/purchasesAndSales/show/:identificationNumber/purchases" element={<PurchasesInvoice/>} />
          <Route path="/purchasesAndSales/show/:identificationNumber/sales" element={<SalesInvoice />} />
          
        
      
        <Route index element={<Navigate to={"/invoices"}/>}/>
          <Route path="/invoices">
            <Route index element={<InvoiceIndex />}/>
            <Route path="show/:id" element={<InvoiceDetail/> } />
            <Route path="create" element={<InvoiceForm />} />
            <Route path="edit/:id" element={<InvoiceForm />}/>
          </Route>

        <Route index element={<Navigate to={"/persons/statistics"}/>}/>
          <Route path="/persons/statistics">
            <Route index element={<PersonStatistics />}/>
        </Route>
        <Route index element={<Navigate to={"/invoices/statistics"}/>}/>
          <Route path="/invoices/statistics">
            <Route index element={<InvoiceStatistics />}/>
        </Route>

        </Routes>
      </div>
    </Router>
  );
}

export default App;
