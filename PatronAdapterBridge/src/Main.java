import java.util.Scanner;

interface ChannelImplementor {
    void sendMessage(String recipient, String subject, String content, String priority);
}

class EmailChannelImpl implements ChannelImplementor {
    private EmailAdapter emailAdapter;

    public EmailChannelImpl(EmailAdapter emailAdapter) {
        this.emailAdapter = emailAdapter;
    }

    @Override
    public void sendMessage(String recipient, String subject, String content, String priority) {
        System.out.println("Preparando envío por email...");
        emailAdapter.send(recipient, subject, content, priority);
    }
}

class SMSChannelImpl implements ChannelImplementor {
    private SMSAdapter smsAdapter;

    public SMSChannelImpl(SMSAdapter smsAdapter) {
        this.smsAdapter = smsAdapter;
    }

    @Override
    public void sendMessage(String recipient, String subject, String content, String priority) {
        System.out.println("Preparando envío por SMS...");
        String fullMessage = subject + ": " + content;
        smsAdapter.sendSMS(recipient, fullMessage, priority);
    }
}

class SlackChannelImpl implements ChannelImplementor {
    private SlackAdapter slackAdapter;

    public SlackChannelImpl(SlackAdapter slackAdapter) {
        this.slackAdapter = slackAdapter;
    }

    @Override
    public void sendMessage(String recipient, String subject, String content, String priority) {
        System.out.println("Preparando envío por Slack...");
        slackAdapter.postMessage(recipient, subject, content, priority);
    }
}

abstract class Notification {
    protected ChannelImplementor channel;

    public Notification(ChannelImplementor channel) {
        this.channel = channel;
    }

    public abstract void send(String recipient, String subject, String content);
}

class UrgentNotification extends Notification {
    public UrgentNotification(ChannelImplementor channel) {
        super(channel);
    }

    @Override
    public void send(String recipient, String subject, String content) {
        System.out.println("NOTIFICACIÓN URGENTE: Enviando notificación urgente...");
        String urgentSubject = "URGENTE: " + subject;
        String urgentContent = "ATENCIÓN INMEDIATA REQUERIDA:\n" + content;
        channel.sendMessage(recipient, urgentSubject, urgentContent, "HIGH");
    }
}

class InformativeNotification extends Notification {
    public InformativeNotification(ChannelImplementor channel) {
        super(channel);
    }

    @Override
    public void send(String recipient, String subject, String content) {
        System.out.println("NOTIFICACIÓN INFORMATIVA: Enviando notificación informativa...");
        String informativeSubject = "Info: " + subject;
        channel.sendMessage(recipient, informativeSubject, content, "MEDIUM");
    }
}

class MarketingNotification extends Notification {
    public MarketingNotification(ChannelImplementor channel) {
        super(channel);
    }

    @Override
    public void send(String recipient, String subject, String content) {
        System.out.println("NOTIFICACIÓN DE MARKETING: Enviando notificación de marketing...");
        String marketingSubject = subject;
        String marketingContent = content + "\n\nGracias por ser parte de nuestra comunidad!";
        channel.sendMessage(recipient, marketingSubject, marketingContent, "LOW");
    }
}

interface EmailAdapter {
    void send(String to, String subject, String body, String priority);
}

interface SMSAdapter {
    void sendSMS(String phoneNumber, String message, String priority);
}

interface SlackAdapter {
    void postMessage(String channel, String title, String message, String priority);
}

class OutlookExchangeServer {
    public void sendExchangeEmail(String toAddress, String emailSubject,
                                  String emailBody, boolean isHighPriority) {
        System.out.println("SERVIDOR EXCHANGE: Enviando email:");
        System.out.println("   Para: " + toAddress);
        System.out.println("   Asunto: " + emailSubject);
        System.out.println("   Prioridad Alta: " + isHighPriority);
        System.out.println("   Email enviado via Exchange");
    }
}

class TwilioSMSService {
    public boolean transmitSMS(String destinationNumber, String textContent,
                               String urgencyLevel) {
        System.out.println("API TWILIO: Enviando SMS:");
        System.out.println("   Destino: " + destinationNumber);
        System.out.println("   Mensaje: " + textContent);
        System.out.println("   Urgencia: " + urgencyLevel);
        System.out.println("   SMS enviado via Twilio");
        return true;
    }
}

class LegacySMTPServer {
    public void relayMessage(String recipient, String title, String body, int priorityCode) {
        System.out.println("SMTP LEGACY: Enviando email:");
        System.out.println("   Destinatario: " + recipient);
        System.out.println("   Título: " + title);
        System.out.println("   Código Prioridad: " + priorityCode);
        System.out.println("   Email enviado via SMTP Legacy");
    }
}

class SlackWebAPI {
    public void publishMessage(String workspace, String channelName,
                               String messageTitle, String messageBody, boolean isPriority) {
        System.out.println("API SLACK: Publicando mensaje:");
        System.out.println("   Canal: " + channelName);
        System.out.println("   Título: " + messageTitle);
        System.out.println("   Es Prioritario: " + isPriority);
        System.out.println("   Mensaje publicado en Slack");
    }
}

class OutlookExchangeAdapter implements EmailAdapter {
    private OutlookExchangeServer exchangeServer;

    public OutlookExchangeAdapter(OutlookExchangeServer exchangeServer) {
        this.exchangeServer = exchangeServer;
    }

    @Override
    public void send(String to, String subject, String body, String priority) {
        boolean isHighPriority = "HIGH".equals(priority);
        exchangeServer.sendExchangeEmail(to, subject, body, isHighPriority);
    }
}

class LegacySMTPAdapter implements EmailAdapter {
    private LegacySMTPServer smtpServer;

    public LegacySMTPAdapter(LegacySMTPServer smtpServer) {
        this.smtpServer = smtpServer;
    }

    @Override
    public void send(String to, String subject, String body, String priority) {
        int priorityCode = convertPriorityToCode(priority);
        smtpServer.relayMessage(to, subject, body, priorityCode);
    }

    private int convertPriorityToCode(String priority) {
        switch (priority) {
            case "HIGH": return 1;
            case "MEDIUM": return 2;
            case "LOW": return 3;
            default: return 2;
        }
    }
}

class TwilioSMSAdapter implements SMSAdapter {
    private TwilioSMSService twilioService;

    public TwilioSMSAdapter(TwilioSMSService twilioService) {
        this.twilioService = twilioService;
    }

    @Override
    public void sendSMS(String phoneNumber, String message, String priority) {
        String urgencyLevel = priority.toLowerCase() + "_urgency";
        twilioService.transmitSMS(phoneNumber, message, urgencyLevel);
    }
}

class SlackWebAPIAdapter implements SlackAdapter {
    private SlackWebAPI slackAPI;

    public SlackWebAPIAdapter(SlackWebAPI slackAPI) {
        this.slackAPI = slackAPI;
    }

    @Override
    public void postMessage(String channel, String title, String message, String priority) {
        boolean isPriority = "HIGH".equals(priority);
        slackAPI.publishMessage("empresa-workspace", channel, title, message, isPriority);
    }
}

class NotificationFactory {

    public static Notification createUrgentEmailNotification() {
        OutlookExchangeServer exchangeServer = new OutlookExchangeServer();
        EmailAdapter emailAdapter = new OutlookExchangeAdapter(exchangeServer);
        ChannelImplementor emailChannel = new EmailChannelImpl(emailAdapter);
        return new UrgentNotification(emailChannel);
    }

    public static Notification createInformativeSMSNotification() {
        TwilioSMSService twilioService = new TwilioSMSService();
        SMSAdapter smsAdapter = new TwilioSMSAdapter(twilioService);
        ChannelImplementor smsChannel = new SMSChannelImpl(smsAdapter);
        return new InformativeNotification(smsChannel);
    }

    public static Notification createMarketingSlackNotification() {
        SlackWebAPI slackAPI = new SlackWebAPI();
        SlackAdapter slackAdapter = new SlackWebAPIAdapter(slackAPI);
        ChannelImplementor slackChannel = new SlackChannelImpl(slackAdapter);
        return new MarketingNotification(slackChannel);
    }
}

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(" SISTEMA DE NOTIFICACIONES EMPRESARIALES");

        boolean continueRunning = true;
        while (continueRunning) {
            showMainMenu();
            int option = readOption();

            switch (option) {
                case 1:
                    sendCustomNotification();
                    break;
                case 2:
                    usePredefinedNotifications();
                    break;
                case 3:
                    System.out.println("\n¡Gracias por usar el sistema de notificaciones!");
                    continueRunning = false;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione 1, 2 o 3.");
            }
        }

        scanner.close();
    }

    private static void showMainMenu() {
        System.out.println("\n MENU PRINCIPAL ");
        System.out.println("1. Crear notificación personalizada");
        System.out.println("2. Usar notificaciones predefinidas");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int readOption() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void sendCustomNotification() {
        System.out.println("\n CREAR NOTIFICACIÓN PERSONALIZADA ");

        String notificationType = selectNotificationType();
        String channel = selectChannel();

        System.out.print("Destinatario (email/teléfono/canal): ");
        String recipient = scanner.nextLine();

        System.out.print("Asunto: ");
        String subject = scanner.nextLine();

        System.out.print("Mensaje: ");
        String message = scanner.nextLine();

        Notification notification = createNotification(notificationType, channel);

        System.out.println("\n--- ENVIANDO NOTIFICACIÓN ---");
        notification.send(recipient, subject, message);

        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    private static String selectNotificationType() {
        System.out.println("\nTipos de notificación:");
        System.out.println("1. Urgente");
        System.out.println("2. Informativa");
        System.out.println("3. Marketing");
        System.out.print("Seleccione el tipo (1-3): ");

        int option = readOption();
        switch (option) {
            case 1: return "urgent";
            case 2: return "informative";
            case 3: return "marketing";
            default:
                System.out.println("Opción inválida, usando 'informativa' por defecto.");
                return "informative";
        }
    }

    private static String selectChannel() {
        System.out.println("\nCanales disponibles:");
        System.out.println("1. Email (Exchange Server)");
        System.out.println("2. Email (SMTP Legacy)");
        System.out.println("3. SMS (Twilio)");
        System.out.println("4. Slack");
        System.out.print("Seleccione el canal (1-4): ");

        int option = readOption();
        switch (option) {
            case 1: return "email-exchange";
            case 2: return "email-smtp";
            case 3: return "sms";
            case 4: return "slack";
            default:
                System.out.println("Opción inválida, usando 'email-exchange' por defecto.");
                return "email-exchange";
        }
    }

    private static Notification createNotification(String type, String channel) {
        ChannelImplementor channelImpl = createChannel(channel);

        switch (type) {
            case "urgent":
                return new UrgentNotification(channelImpl);
            case "informative":
                return new InformativeNotification(channelImpl);
            case "marketing":
                return new MarketingNotification(channelImpl);
            default:
                return new InformativeNotification(channelImpl);
        }
    }

    private static ChannelImplementor createChannel(String channel) {
        switch (channel) {
            case "email-exchange":
                OutlookExchangeServer exchangeServer = new OutlookExchangeServer();
                EmailAdapter exchangeAdapter = new OutlookExchangeAdapter(exchangeServer);
                return new EmailChannelImpl(exchangeAdapter);

            case "email-smtp":
                LegacySMTPServer smtpServer = new LegacySMTPServer();
                EmailAdapter smtpAdapter = new LegacySMTPAdapter(smtpServer);
                return new EmailChannelImpl(smtpAdapter);

            case "sms":
                TwilioSMSService twilioService = new TwilioSMSService();
                SMSAdapter smsAdapter = new TwilioSMSAdapter(twilioService);
                return new SMSChannelImpl(smsAdapter);

            case "slack":
                SlackWebAPI slackAPI = new SlackWebAPI();
                SlackAdapter slackAdapter = new SlackWebAPIAdapter(slackAPI);
                return new SlackChannelImpl(slackAdapter);

            default:
                OutlookExchangeServer defaultServer = new OutlookExchangeServer();
                EmailAdapter defaultAdapter = new OutlookExchangeAdapter(defaultServer);
                return new EmailChannelImpl(defaultAdapter);
        }
    }

    private static void usePredefinedNotifications() {
        System.out.println("\n NOTIFICACIONES PREDEFINIDAS ");
        System.out.println("1. Notificación Urgente por Email");
        System.out.println("2. Notificación Informativa por SMS");
        System.out.println("3. Notificación de Marketing por Slack");
        System.out.println("4. Ejecutar todas las anteriores");
        System.out.print("Seleccione una opción (1-4): ");

        int option = readOption();

        switch (option) {
            case 1:
                System.out.println("\n NOTIFICACIÓN URGENTE POR EMAIL ");
                Notification urgentEmail = NotificationFactory.createUrgentEmailNotification();
                urgentEmail.send("admin@empresa.com", "Servidor Caído",
                        "El servidor de producción ha dejado de responder.");
                break;

            case 2:
                System.out.println("\n NOTIFICACIÓN INFORMATIVA POR SMS ");
                Notification infoSMS = NotificationFactory.createInformativeSMSNotification();
                infoSMS.send("+1234567890", "Mantenimiento Programado",
                        "El sistema estará en mantenimiento el domingo de 2-4 AM.");
                break;

            case 3:
                System.out.println("\n NOTIFICACIÓN DE MARKETING POR SLACK ");
                Notification marketingSlack = NotificationFactory.createMarketingSlackNotification();
                marketingSlack.send("#general", "Nueva Funcionalidad",
                        "Ahora puedes personalizar tu dashboard!");
                break;

            case 4:
                System.out.println("\n EJECUTANDO TODAS LAS NOTIFICACIONES ");

                System.out.println("\n1. Notificación Urgente por Email:");
                Notification urgentEmail2 = NotificationFactory.createUrgentEmailNotification();
                urgentEmail2.send("admin@empresa.com", "Servidor Caído",
                        "El servidor de producción ha dejado de responder.");

                System.out.println("\n2. Notificación Informativa por SMS:");
                Notification infoSMS2 = NotificationFactory.createInformativeSMSNotification();
                infoSMS2.send("+1234567890", "Mantenimiento Programado",
                        "El sistema estará en mantenimiento el domingo de 2-4 AM.");

                System.out.println("\n3. Notificación de Marketing por Slack:");
                Notification marketingSlack2 = NotificationFactory.createMarketingSlackNotification();
                marketingSlack2.send("#general", "Nueva Funcionalidad",
                        "Ahora puedes personalizar tu dashboard!");

                System.out.println("\n4. Notificación Urgente por SMS (combinación personalizada):");
                TwilioSMSService twilioService = new TwilioSMSService();
                SMSAdapter smsAdapter = new TwilioSMSAdapter(twilioService);
                ChannelImplementor smsChannel = new SMSChannelImpl(smsAdapter);
                Notification urgentSMS = new UrgentNotification(smsChannel);
                urgentSMS.send("+1234567890", "Fallo Crítico",
                        "Base de datos principal no responde.");
                break;

            default:
                System.out.println("Opción no válida.");
        }

        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
}