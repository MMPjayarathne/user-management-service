// __mocks__/react-router-dom.js
export const BrowserRouter = ({ children }) => <div>{children}</div>;
export const Routes = ({ children }) => <div>{children}</div>;
export const Route = ({ element }) => <div>{element}</div>;
export const Navigate = ({ to }) => <div>Navigated to {to}</div>;