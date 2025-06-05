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
  useLocation
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
import RegistrationPage from "./registration/RegistrationPage";
import { useSession } from "./contexts/session";
import { apiDelete } from "./utils/api";
import LoginPage from "./login/LoginPage";


function AppContent() {
  const location = useLocation();
  const showInvoiceStatistics = location.pathname === "/invoices";
  const showPersonStatistics = location.pathname === "/persons";
  const {session, setSession} = useSession();
  const handleLogoutClick = () => {
    apiDelete("/api/auth")
      .finally(() => setSession({ data: null, status: "unauthorized" }));
  }

  return (
    <>
      <nav className="mb-5 navbar navbar-expand-lg navbar-light bg-info w-100">
        <div className="container">
          <ul className="navbar-nav ms col-6">
        
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
            {showInvoiceStatistics && (
              <li className="nav-item">
                <Link to={"/invoices/statistics"} className="nav-link">
                  Statistika faktur
                </Link>
              </li>
            )}
            {showPersonStatistics && (
              <li className="nav-item">
                <Link to={"/persons/statistics"} className="nav-link">
                  Statistika osob
                </Link>
              </li>
            )}
          </ul>
          <ul className="navbar-nav ms">
            {session.data ?
              <>
                <li className="nav-item">
                  {session.data.email}
                </li>
                <li className="nav-item">
                  <button className="" onClick={handleLogoutClick}>Odhlásit se</button>
                </li>
              </> : session.status === "loading" ?
                <>
                  <div className="spinner-border spinner-border-sm" role="status">
                    <span className="visually-hidden">Loading...</span>
                  </div>
                </>
                : <>
                  <li className="nav-item">
                    <Link to={"/register"} className="nav-link">Registrace</Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/login"} className="nav-link">Přihlásit se</Link>
                  </li>
                </>
                }
          </ul>
        </div>
      </nav>
      <div className="container">
        <Routes>
          <Route index element={<Navigate to={"/persons"} />} />
          <Route path="/persons">
            <Route index element={<PersonIndex />} />
            <Route path="show/:id" element={<PersonDetail />} />
            <Route path="create" element={<PersonForm />} />
            <Route path="edit/:id" element={<PersonForm />} />
          </Route>
          <Route path="/purchasesAndSales/show/:identificationNumber/purchases" element={<PurchasesInvoice />} />
          <Route path="/purchasesAndSales/show/:identificationNumber/sales" element={<SalesInvoice />} />
          <Route path="/invoices">
            <Route index element={<InvoiceIndex />} />
            <Route path="show/:id" element={<InvoiceDetail />} />
            <Route path="create" element={<InvoiceForm />} />
            <Route path="edit/:id" element={<InvoiceForm />} />
          </Route>
          <Route path="/persons/statistics">
            <Route index element={<PersonStatistics />} />
          </Route>
          <Route path="/invoices/statistics">
            <Route index element={<InvoiceStatistics />} />
          </Route>
          <Route path="/register" element={<RegistrationPage />} />
          <Route path="/login" element={<LoginPage/>}/>
        </Routes>
      </div>
    </>
  );
}

export default function App() {
  return (
    <Router>
      <AppContent />
    </Router>
  );
}

