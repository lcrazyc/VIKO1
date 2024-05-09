using System;
using System.IO;
using System.Net.Sockets;
using System.Security.Cryptography;
using System.Text.Json;
using System.Threading.Tasks;

namespace IntermediateServer
{
    class Program
    {
        static async Task Main(string[] args)
        {
            await StartServer();
        }

        static async Task StartServer()
        {
            try
            {
                TcpListener listener = new TcpListener(System.Net.IPAddress.Any, 3038);
                listener.Start();
                Console.WriteLine("Intermediate Server started. Waiting for data...");

                while (true)
                {
                    using (TcpClient client = await listener.AcceptTcpClientAsync())
                    {
                        await HandleClient(client);
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error in StartServer: {ex.Message}");
            }
        }

        static async Task HandleClient(TcpClient client)
        {
            try
            {
                string? jsonData = await NetworkHelper.ReceiveDataAsync(client);
                if (jsonData == null) throw new Exception("No data received");

                SignatureData data = JsonSerializer.Deserialize<SignatureData>(jsonData);

                byte[] changedSignature = ChangeSignature(data.Signature);

                data.Signature = changedSignature;

                await SendDataToThirdApplication(data);
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error in HandleClient: {ex.Message}");
            }
        }

        static byte[] ChangeSignature(byte[] signature)
        {
            Console.WriteLine("Do you want to change the signature? (Y/N)");
            string answer = Console.ReadLine()?.Trim().ToUpper();

            if (answer == "Y")
            {
                Console.WriteLine("Enter the new signature:");
                string newSignatureString = Console.ReadLine() ?? "";
                return Convert.FromBase64String(newSignatureString);
            }
            else
            {
                return signature;
            }
        }

        static async Task SendDataToThirdApplication(SignatureData data)
        {
            try
            {
                using (TcpClient client = new TcpClient("localhost", 4040)) // Port of the third application
                using (NetworkStream stream = client.GetStream())
                using (StreamWriter writer = new StreamWriter(stream))
                {
                    string jsonData = JsonSerializer.Serialize(data);
                    await writer.WriteAsync(jsonData);
                    await writer.FlushAsync();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error in SendDataToThirdApplication: {ex.Message}");
            }
        }
    }

    public class SignatureData
    {
        public byte[] Signature { get; set; }
        public string Text { get; set; }
        public RSAParametersData PublicKey { get; set; }
    }

    public class RSAParametersData
    {
        public byte[] Modulus { get; set; }
        public byte[] Exponent { get; set; }

        public RSAParametersData(byte[] modulus, byte[] exponent)
        {
            Modulus = modulus;
            Exponent = exponent;
        }
    }
}
